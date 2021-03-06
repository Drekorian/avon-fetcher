import org.gradle.jvm.tasks.Jar
import java.net.URI
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.50"
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
    compile("com.github.jkcclemens", "khttp", "-SNAPSHOT")
    compile("io.github.microutils:kotlin-logging:1.6.22")
    compile("org.slf4j", "slf4j-simple", "1.7.25")
    compile("org.jsoup", "jsoup", "1.11.3")
    testCompile("junit", "junit", "4.12")
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

tasks.withType<Jar> {
    archiveName = "avon-fetcher.jar"
    manifest.attributes.put("Main-Class", "cz.drekorian.avonfetcher.Main")
    from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
}

//jar {
//    archiveName = "avon-fetcher.jar"
//
//    from {
//
//        configurations.runtime.collect {
//            it.isDirectory() ? it : zipTree(it)
//        }
//
//        configurations.compile.collect {
//            it.isDirectory() ? it : zipTree(it)
//        }
//    }
//
//    manifest {
//        attributes 'Main-Class': 'cz.drekorian.avonfetcher'
//    }

//    exclude 'META-INF/*.RSA', 'META-INF/*.SF','META-INF/*.DSA'
//}
