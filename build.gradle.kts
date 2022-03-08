buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.build.spotless)
        classpath(libs.build.r8)
        classpath(libs.build.gradle)
        classpath(libs.build.gradle.kotlin)
        classpath(libs.build.gradle.dagger)
        classpath(libs.build.navigation.safeargs)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

apply("buildscripts/githooks.gradle")
