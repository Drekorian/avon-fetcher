package cz.drekorian.avonfetcher.http.brochure.summaries

import cz.drekorian.avonfetcher.I18n
import cz.drekorian.avonfetcher.http.BASE_URL
import cz.drekorian.avonfetcher.http.KHttpClient
import cz.drekorian.avonfetcher.http.Request
import cz.drekorian.avonfetcher.http.baseParams
import khttp.responses.Response
import mu.KotlinLogging
import org.json.JSONArray

val logger = KotlinLogging.logger {}

class BrochureSummariesRequest : Request() {

    companion object {
        private const val URL_PREFIX = "$BASE_URL/BrochureSummariesJson"
        private const val KEY_DATA = "Data"
    }

    fun send(campaignId: String): BrochureSummariesResponse? {
        val response: Response = KHttpClient.get(url = URL_PREFIX, params = baseParams(campaignId))
        if (!checkStatusCode(response, logger, I18n.get("brochure_summaries_request_ok"))) {
            return null
        }

        val data: JSONArray? = response.jsonObject.optJSONArray(KEY_DATA)
        return if (data != null) BrochureSummariesResponse(data) else null
    }
}
