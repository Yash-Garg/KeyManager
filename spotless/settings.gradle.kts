@file:Suppress("UnstableApiUsage")

rootProject.name = "spotless"

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        versionCatalogs { create("libs") { from(files("../gradle/libs.versions.toml")) } }
    }
}
