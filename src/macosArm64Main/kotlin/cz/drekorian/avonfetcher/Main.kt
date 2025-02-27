package cz.drekorian.avonfetcher

import mu.ConsoleOutputAppender
import mu.KotlinLoggingConfiguration
import mu.KotlinLoggingLevel

internal actual fun enableDebugLogging() {
    KotlinLoggingConfiguration.appender = ConsoleOutputAppender
    KotlinLoggingConfiguration.logLevel = KotlinLoggingLevel.DEBUG
}
