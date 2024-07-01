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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
