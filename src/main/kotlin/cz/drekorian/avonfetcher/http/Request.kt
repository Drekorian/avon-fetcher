package cz.drekorian.avonfetcher.http

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import org.slf4j.Logger

abstract class Request {
    protected suspend fun checkStatusCode(response: HttpResponse, logger: Logger, errorMessage: String): Boolean {
        val statusCode = response.status
        if (statusCode != HttpStatusCode.OK) {
            logger.error("$errorMessage ($statusCode) ${response.body<String>()}")
            return false
        }

        return true
    }
}
