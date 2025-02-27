@file:JvmName("Main")

package cz.drekorian.avonfetcher

import org.slf4j.simple.SimpleLogger

internal actual val platformUsage: String = "usage_jvm"

internal actual fun enableDebugLogging() {
    System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG")
}
