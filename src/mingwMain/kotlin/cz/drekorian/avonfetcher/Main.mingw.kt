package cz.drekorian.avonfetcher

import mu.KotlinLoggingConfiguration
import mu.KotlinLoggingLevel

/**
 * Enables debug-level logs.
 */
internal actual fun enableDebugLogging() {
    KotlinLoggingConfiguration.logLevel = KotlinLoggingLevel.DEBUG
}

internal actual val platformUsage: String = "usage_mingw"
