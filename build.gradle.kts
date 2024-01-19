import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    java
    kotlin("jvm") version libs.versions.kotlin.get()
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
    implementation(libs.kotlin.logging)
    implementation(libs.sl4j.simple)
    implementation(libs.jsoup)

    testImplementation(libs.junit)
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
