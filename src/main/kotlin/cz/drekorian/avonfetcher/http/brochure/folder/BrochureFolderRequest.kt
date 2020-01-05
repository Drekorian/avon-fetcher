package cz.drekorian.avonfetcher.http.brochure.folder

import cz.drekorian.avonfetcher.I18n
import cz.drekorian.avonfetcher.http.*
import khttp.responses.Response
import mu.KotlinLogging

val logger = KotlinLogging.logger { }

class BrochureFolderRequest : Request() {

    companion object {
        private const val URL = "$BASE_URL/BrochureFolderJson"
        private const val QUERY_KEY_FOLDER = "folder"
    }

    fun send(campaignId: String, folder: String): BrochureFolderResponse? {
        val params: Map<String, String> =
            HashMap<String, String>(baseParams(campaignId)).apply { put(QUERY_KEY_FOLDER, folder) }
        val response: Response = KHttpClient.get(url = URL, params = params)

        if (!checkStatusCode(response, logger, I18n.get("brochure_folder_request_error"))) {
            return null
        }

        return BrochureFolderResponse(response.jsonObject)
    }
}
