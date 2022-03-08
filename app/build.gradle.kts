import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.diffplug.spotless")
}

android {
    compileSdk = 32
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "dev.yash.keymanager"
        minSdk = 26
        targetSdk = 32
        versionCode = 2
        versionName = "1.1"

        manifestPlaceholders["appAuthRedirectScheme"] = "dev.yash.keymanager"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures { viewBinding = true }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/*.kotlin_module"
            excludes += "**/kotlin/**"
            excludes += "**/*.txt"
            excludes += "**/*.xml"
            excludes += "**/*.properties"
        }
    }

    kapt { correctErrorTypes = true }

    val keystoreConfigFile = rootProject.layout.projectDirectory.file("key.properties")
    if (keystoreConfigFile.asFile.exists()) {
        val contents = providers.fileContents(keystoreConfigFile).asText.forUseAtConfigurationTime()
        val keystoreProperties = Properties()
        keystoreProperties.load(contents.get().byteInputStream())
        signingConfigs {
            register("release") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = rootProject.file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
        buildTypes.all { signingConfig = signingConfigs.getByName("release") }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
}


spotless {
    kotlin {
        ktfmt().kotlinlangStyle()
        target("**/*.kt")
        targetExclude("**/build/")
    }
    groovyGradle {
        target("**/*.gradle")
        targetExclude("**/build/")
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/", ".idea/")
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.legacy)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.security.identity)

    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.no.op)

    implementation(libs.google.dagger)
    kapt(libs.google.dagger.compiler)
    implementation(libs.google.material)

    implementation(libs.square.moshi)
    implementation(libs.square.moshi.converter)
    implementation(libs.moshi.metadata.reflect)
    implementation(libs.square.okhttp.logging)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.ktfmt)

    debugImplementation(libs.leakcanary)
    implementation(libs.lottie)
    implementation(libs.openid.appauth)
    implementation(libs.timber)
}
