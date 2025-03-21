package cz.drekorian.avonfetcher.multiplatform.util.http.brochure.folder

import cz.drekorian.avonfetcher.model.brochure.folder.Data
import cz.drekorian.avonfetcher.model.brochure.folder.Folder
import cz.drekorian.avonfetcher.multiplatform.util.http.BASE_HOST
import cz.drekorian.avonfetcher.multiplatform.util.http.BASE_PATH_API
import cz.drekorian.avonfetcher.multiplatform.util.http.BASE_PATH_BROCHURE_API
import cz.drekorian.avonfetcher.multiplatform.util.http.KtorHttpClient
import cz.drekorian.avonfetcher.multiplatform.util.http.Request
import cz.drekorian.avonfetcher.multiplatform.util.http.baseParams
import cz.drekorian.avonfetcher.multiplatform.util.http.json
import cz.drekorian.avonfetcher.resources.I18n
import io.ktor.client.call.body
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

class BrochureFolderRequest : Request() {

    companion object {

        private const val QUERY_KEY_FOLDER = "folder"
    }

    suspend fun send(campaignId: String, folder: String): BrochureFolderResponse? {
        val response = KtorHttpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_HOST
                appendPathSegments(BASE_PATH_API, BASE_PATH_BROCHURE_API, "BrochureFolderJson")
                for ((key, value) in baseParams(campaignId)) {
                    parameters.append(key, value)
                }
                parameters.append(QUERY_KEY_FOLDER, folder)
            }
        }

        if (!checkStatusCode(response, I18n.get("brochure_folder_request_error"))) {
            return null
        }

        val responseFolder = response.body<Folder>()
        return json.decodeFromString<Data?>(responseFolder.data)?.let { data -> BrochureFolderResponse(data.pages) }
    }
}
