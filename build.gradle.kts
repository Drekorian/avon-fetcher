import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    alias(libs.plugins.shadow)
}

group = "cz.drekorian"
version = "beta7"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlin.logging)
    implementation(libs.kotlin.logging.jvm)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.sl4j.simple)

    testImplementation(libs.junit)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    tasks {
        shadowJar {
            archiveBaseName.set(rootProject.name)
            archiveClassifier.set("")
            archiveVersion.set("${project.version}")
            archiveAppendix.set("all")

            manifest {
                attributes("Main-Class" to "${project.group}.Main")
            }
            mergeServiceFiles()
        }

        withType<KotlinJvmCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}
