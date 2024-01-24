package cz.drekorian.avonfetcher.http

import cz.drekorian.avonfetcher.logger
import io.ktor.client.plugins.logging.Logger

object HttpLogger : Logger {
    override fun log(message: String) {
        logger.debug { message }
    }
}
