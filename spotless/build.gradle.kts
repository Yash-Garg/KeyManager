import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { `kotlin-dsl` }

dependencies { implementation(libs.build.spotless) }

afterEvaluate {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions { jvmTarget = JavaVersion.VERSION_17.toString() }
    }
}

gradlePlugin {
    plugins {
        register("spotless") {
            id = "dev.yash.keymanager.spotless"
            implementationClass = "dev.yash.keymanager.gradle.SpotlessPlugin"
            version = "1.0.0"
        }
        register("githooks") {
            id = "dev.yash.keymanager.githooks"
            implementationClass = "dev.yash.keymanager.gradle.GitHooksPlugin"
            version = "1.0.0"
        }
    }
}
