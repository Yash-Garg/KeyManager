@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

import java.util.*

val isGithubCi = System.getenv("GITHUB_CI") != null

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "dev.yash.keymanager"
    compileSdk = 33

    defaultConfig {
        applicationId = "dev.yash.keymanager"
        minSdk = 26
        targetSdk = 33
        versionCode = 8
        versionName = "v3.1"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "dev.yash.keymanager"
        setProperty("archivesBaseName", "${defaultConfig.applicationId}-$versionName")

        vectorDrawables { useSupportLibrary = true }
    }

    kapt { correctErrorTypes = true }

    val keystoreConfigFile = rootProject.layout.projectDirectory.file("key.properties")
    if (keystoreConfigFile.asFile.exists()) {
        val contents = providers.fileContents(keystoreConfigFile).asText
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
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            versionNameSuffix = "-release"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        if (isGithubCi) {
            configureEach {
                buildConfigField("String", "CLIENT_ID", "\"${System.getenv("CLIENT_ID")}\"")
                buildConfigField("String", "CLIENT_SECRET", "\"${System.getenv("CLIENT_SECRET")}\"")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions { jvmTarget = JavaVersion.VERSION_1_8.toString() }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion =
            libs.compose.compiler.get().versionConstraint.requiredVersion
    }

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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.ktx)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.security.identity)

    implementation(libs.compose.activity)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.navigation.hilt)
    implementation(libs.compose.paging)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.square.moshi)
    implementation(libs.square.moshi.converter)
    implementation(libs.square.moshi.metadata.reflect)
    implementation(libs.square.okhttp.logging)
    debugImplementation(libs.square.leakcanary)

    implementation(libs.google.accompanist.systemuicontroller)
    implementation(libs.google.dagger.hilt)
    kapt(libs.google.dagger.hilt.compiler)

    implementation(libs.appauth)
    implementation(libs.kotlin.result)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
    testImplementation(libs.junit)
}
