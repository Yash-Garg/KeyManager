plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = Constants.Sdk.targetSdkVersion
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "app.yash.gisthub"
        minSdk = Constants.Sdk.minSdkVersion
        targetSdk = Constants.Sdk.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Constants.Dependencies.kotlin_version}")
    implementation("androidx.core:core-ktx:${Constants.Dependencies.core_ktx_version}")
    implementation("androidx.appcompat:appcompat:${Constants.Dependencies.appcompat_version}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Material Design and Layouts
    implementation("com.google.android.material:material:${Constants.Dependencies.material_version}")
    implementation("androidx.constraintlayout:constraintlayout:${Constants.Dependencies.constraint_layout_version}")

    // Room
    implementation("androidx.room:room-runtime:${Constants.Dependencies.room_version}")
    annotationProcessor("androidx.room:room-compiler:${Constants.Dependencies.room_version}")
    kapt("androidx.room:room-compiler:${Constants.Dependencies.room_version}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Constants.Dependencies.retrofit_version}")
    implementation("com.squareup.retrofit2:converter-gson:${Constants.Dependencies.retrofit_version}")

    // OkHTTP3
    implementation("com.squareup.okhttp3:okhttp:${Constants.Dependencies.okhttp_version}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Constants.Dependencies.okhttp_version}")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Constants.Dependencies.nav_version}")
    implementation("androidx.navigation:navigation-ui-ktx:${Constants.Dependencies.nav_version}")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Constants.Dependencies.lifecycle_version}")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Constants.Dependencies.lifecycle_version}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}