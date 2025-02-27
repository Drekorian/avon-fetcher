package cz.drekorian.avonfetcher.multiplatform.util.http

import cz.drekorian.avonfetcher.logger
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

/**
 * This class represents a single HTTP request.
 *
 * @see cz.drekorian.avonfetcher.http.brochure.folder.BrochureFolderRequest
 * @see cz.drekorian.avonfetcher.http.brochure.summaries.BrochureSummariesRequest
 * @see cz.drekorian.avonfetcher.http.productlistmodal.ProductListModalRequest
 *
 * @author Marek Osvald
 */
abstract class Request {

    private val HttpResponse.isOk: Boolean
        get() = status == HttpStatusCode.OK

    protected suspend fun checkStatusCode(response: HttpResponse, errorMessage: String): Boolean {
        if (!response.isOk) {
            val body = response.body<String>()
            logger.error { "$errorMessage (${response.status}) $body" }
            return false
        }

        return true
    }
}
