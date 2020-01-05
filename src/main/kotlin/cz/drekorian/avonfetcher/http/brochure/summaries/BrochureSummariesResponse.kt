package cz.drekorian.avonfetcher.http.brochure.summaries

import cz.drekorian.avonfetcher.model.brochure.Folder
import org.json.JSONArray
import org.json.JSONObject

class BrochureSummariesResponse(data: JSONArray) {
    val folders = data.map { Folder(it as JSONObject) }
}
