@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "dev.yash.keymanager.data"
    compileSdk = 34

    defaultConfig { minSdk = 26 }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures { buildConfig = true }

    buildTypes {
        configureEach {
            buildConfigField("String", "CLIENT_ID", "\"${System.getenv("CLIENT_ID")}\"")
            buildConfigField("String", "CLIENT_SECRET", "\"${System.getenv("CLIENT_SECRET")}\"")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.google.dagger.hilt)

    implementation(libs.appauth)
    implementation(libs.square.moshi)
    implementation(libs.square.moshi.converter)
    implementation(libs.square.moshi.metadata.reflect)
    implementation(libs.square.okhttp.logging)
}
