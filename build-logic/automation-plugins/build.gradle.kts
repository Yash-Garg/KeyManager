import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { `kotlin-dsl` }

afterEvaluate {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions { jvmTarget = JavaVersion.VERSION_11.toString() }
    }
}

gradlePlugin {
    plugins {
        register("githooks") {
            id = "dev.yash.keymanager.githooks"
            implementationClass = "dev.yash.keymanager.gradle.GitHooksPlugin"
        }
    }
}
