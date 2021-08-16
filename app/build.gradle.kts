plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
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

    buildTypes {
        release {
            isMinifyEnabled = true
            setProguardFiles(listOf("proguard-android-optimize.txt", "proguard-rules.pro"))
        }
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
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Dependencies.kotlin_version}")
    implementation("androidx.core:core-ktx:${Dependencies.core_ktx_version}")
    implementation("androidx.appcompat:appcompat:${Dependencies.appcompat_version}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // AppAuth
    implementation("net.openid:appauth:${Dependencies.appauth_version}")

    // Design
    implementation("com.google.android.material:material:${Dependencies.material_version}")
    implementation("androidx.constraintlayout:constraintlayout:${Dependencies.constraint_layout_version}")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:${Dependencies.retrofit_version}")
    implementation("com.squareup.retrofit2:converter-gson:${Dependencies.retrofit_version}")

    // Retrofit with Moshi
    implementation("com.squareup.moshi:moshi-kotlin:${Dependencies.moshi_version}")
    implementation("com.squareup.retrofit2:converter-moshi:${Dependencies.retrofit_version}")
    implementation("dev.zacsweers.moshix:moshi-metadata-reflect:${Dependencies.reflect_version}")

    // Security
    implementation("androidx.security:security-crypto:${Dependencies.crypto_version}")
    implementation("androidx.security:security-identity-credential:${Dependencies.identity_version}")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Dependencies.lifecycle_version}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Dependencies.lifecycle_version}")
}
