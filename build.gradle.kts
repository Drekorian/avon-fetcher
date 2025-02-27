import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
}

group = "cz.drekorian.avonfetcher"
version = "2.0.0"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    jvm {
        compilations.named("main") {
            tasks {
                register<ShadowJar>("shadowJarJvm") {
                    group = "build"
                    from(output)
                    configurations = listOf(runtimeDependencyFiles)

                    archiveBaseName.set(rootProject.name)
                    archiveClassifier.set("")
                    archiveVersion.set("${project.version}")
                    archiveAppendix.set("all")

                    manifest {
                        attributes("Main-Class" to "${project.group}.MainKt")
                    }
                    mergeServiceFiles()
                }.also { shadowJar ->
                    getByName("${this@jvm.targetName}Jar") {
                        finalizedBy(shadowJar)
                    }
                }
            }
        }
    }

    macosArm64 {
        binaries {
            executable {
                baseName = buildString {
                    append(rootProject.name)
                    append("-macos-arm64")
                    if (buildType == NativeBuildType.DEBUG) {
                        append("-debug")
                    }
                    append("-$version")
                }
                entryPoint = "${project.group}.main"
            }
        }
    }

    mingwX64 {
        binaries {
            executable {
                baseName = buildString {
                    append(rootProject.name)
                    append("-mingw-x64")
                    if (buildType == NativeBuildType.DEBUG) {
                        append("-debug")
                    }
                    append("-$version")
                }
                entryPoint = "${project.group}.main"
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.logging)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.kotlin.logging.jvm)
                implementation(libs.ktor.client.cio)
                implementation(libs.sl4j.simple)
            }
        }

        macosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        mingwMain {
            dependencies {
                implementation(libs.ktor.client.winhttp)
            }
        }
    }
}
