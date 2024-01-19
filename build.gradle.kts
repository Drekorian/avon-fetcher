import org.gradle.jvm.tasks.Jar
import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.22"
}

group = "cz.drekorian"
version = "1.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = URI("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.jkcclemens", "khttp", "-SNAPSHOT")
    implementation("io.github.microutils:kotlin-logging:1.12.5")
    implementation("org.slf4j", "slf4j-simple", "1.7.36")
    implementation("org.jsoup", "jsoup", "1.11.3")
    testImplementation("junit", "junit", "4.13.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    manifest {
        attributes.put("Main-Class", "cz.drekorian.avonfetcher.Main")
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
