import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
}

group = "cz.drekorian"
version = "beta8"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlin.logging)
    implementation(libs.kotlin.logging.jvm)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.encoding)
    implementation(libs.ktor.client.java)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.sl4j.simple)

    testImplementation(libs.junit)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    tasks {
        shadowJar {
            archiveBaseName.set(rootProject.name)
            archiveClassifier.set("")
            archiveVersion.set("${project.version}")
            archiveAppendix.set("all")

            manifest {
                attributes("Main-Class" to "${project.group}.avonfetcher.Main")
            }
            mergeServiceFiles()
        }
    }

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}
