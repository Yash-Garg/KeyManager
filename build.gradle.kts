buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Constants.Dependencies.gradle_version}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Constants.Dependencies.kotlin_version}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Constants.Dependencies.nav_version}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
