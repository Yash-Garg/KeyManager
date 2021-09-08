buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://storage.googleapis.com/r8-releases/raw")
    }

    dependencies {
        // Refer to https://issuetracker.google.com/issues/194915678#comment14
        classpath("com.android.tools:r8:${Dependencies.r8_version}")
        classpath("com.android.tools.build:gradle:${Dependencies.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.kotlin_version}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Dependencies.hilt_version}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Dependencies.nav_version}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
