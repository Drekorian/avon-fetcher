package cz.drekorian.avonfetcher.http.brochure.summaries

import cz.drekorian.avonfetcher.I18n
import cz.drekorian.avonfetcher.http.BASE_HOST
import cz.drekorian.avonfetcher.http.BASE_PATH_API
import cz.drekorian.avonfetcher.http.BASE_PATH_BROCHURE_API
import cz.drekorian.avonfetcher.http.KtorHttpClient
import cz.drekorian.avonfetcher.http.Request
import cz.drekorian.avonfetcher.http.baseParams
import io.ktor.client.call.body
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

class BrochureSummariesRequest : Request() {

    suspend fun send(campaignId: String): BrochureSummariesResponse? {
        val response = KtorHttpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_HOST
                appendPathSegments(BASE_PATH_API, BASE_PATH_BROCHURE_API, "BrochureSummariesJson")
                for ((key, value) in baseParams(campaignId)) {
                    parameters.append(key, value)
                }
            }
        }

        if (!checkStatusCode(response, logger, I18n.get("brochure_summaries_request_ok"))) {
            return null
        }

        return response.body<BrochureSummariesResponse?>()
    }
}
