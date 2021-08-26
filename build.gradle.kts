buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Dependencies.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
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
