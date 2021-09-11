import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "dev.yash.keymanager"
        minSdk = 24
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["appAuthRedirectScheme"] = "dev.yash.keymanager"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    kapt {
        correctErrorTypes = true
    }

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
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
}

dependencies {
    // Misc.
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Dependencies.kotlin_version}")
    implementation("androidx.core:core-ktx:${Dependencies.core_ktx_version}")
    implementation("androidx.appcompat:appcompat:${Dependencies.appcompat_version}")
    implementation("androidx.activity:activity-ktx:${Dependencies.activity_version}")
    implementation("androidx.fragment:fragment-ktx:${Dependencies.fragment_version}")
    implementation("androidx.legacy:legacy-support-v4:${Dependencies.legacy_version}")

    // AppAuth
    implementation("net.openid:appauth:${Dependencies.appauth_version}")

    // Design
    implementation("com.google.android.material:material:${Dependencies.material_version}")
    implementation("androidx.constraintlayout:constraintlayout:${Dependencies.constraint_layout_version}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Dependencies.hilt_version}")
    kapt("com.google.dagger:hilt-compiler:${Dependencies.hilt_version}")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Dependencies.nav_version}")
    implementation("androidx.navigation:navigation-ui-ktx:${Dependencies.nav_version}")

    // Paging
    implementation("androidx.paging:paging-runtime:${Dependencies.paging_version}")

    // Retrofit with Moshi
    implementation("com.squareup.moshi:moshi-kotlin:${Dependencies.moshi_version}")
    implementation("com.squareup.retrofit2:converter-moshi:${Dependencies.retrofit_version}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Dependencies.logging_version}")
    implementation("dev.zacsweers.moshix:moshi-metadata-reflect:${Dependencies.reflect_version}")

    // Security
    implementation("androidx.security:security-crypto:${Dependencies.crypto_version}")
    implementation("androidx.security:security-identity-credential:${Dependencies.identity_version}")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Dependencies.lifecycle_version}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Dependencies.lifecycle_version}")

    // Lottie
    implementation("com.airbnb.android:lottie:${Dependencies.lottie_version}")

    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:${Dependencies.leakcanary_version}")

    // Chucker
    debugImplementation("com.github.chuckerteam.chucker:library:${Dependencies.chucker_version}")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:${Dependencies.chucker_version}")
}
