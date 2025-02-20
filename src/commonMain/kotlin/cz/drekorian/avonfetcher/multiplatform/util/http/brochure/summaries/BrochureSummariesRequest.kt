package cz.drekorian.avonfetcher.multiplatform.util.http.brochure.summaries

import cz.drekorian.avonfetcher.multiplatform.util.http.BASE_HOST
import cz.drekorian.avonfetcher.multiplatform.util.http.BASE_PATH_API
import cz.drekorian.avonfetcher.multiplatform.util.http.BASE_PATH_BROCHURE_API
import cz.drekorian.avonfetcher.multiplatform.util.http.KtorHttpClient
import cz.drekorian.avonfetcher.multiplatform.util.http.Request
import cz.drekorian.avonfetcher.multiplatform.util.http.baseParams
import cz.drekorian.avonfetcher.resources.I18n
import io.ktor.client.call.body
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

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

        if (!checkStatusCode(response, I18n["brochure_summaries_request_ok"])) {
            return null
        }

        return response.body<BrochureSummariesResponse?>()
    }
}
