package cz.drekorian.avonfetcher.model.brochure

import org.json.JSONObject

class Folder(jsonObject: JSONObject) {

    companion object {
        const val KEY_TITLE = "Title"
        const val KEY_PAGE_COUNT = "PageCount"
        const val KEY_FOLDER = "Folder"
        const val KEY_PUBLISHED = "Published"
    }

    val title = jsonObject.optString(KEY_TITLE)
    val pageCount = jsonObject.optInt(KEY_PAGE_COUNT)
    val folder = jsonObject.optString(KEY_FOLDER)
    val published = jsonObject.optBoolean(KEY_PUBLISHED)
}
