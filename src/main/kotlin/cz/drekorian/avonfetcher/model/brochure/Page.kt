package cz.drekorian.avonfetcher.model.brochure

import org.json.JSONObject

class Page(jsonObject: JSONObject) {

    companion object {
        private const val KEY_PAGE_MIN = "PageMin"
        private const val KEY_PAGE_MAX = "PageMax"
        private const val KEY_HOTSPOTS = "Hotspots"
    }

    @Suppress("MemberVisibilityCanBePrivate")
    val pageMin: Int = jsonObject.optInt(KEY_PAGE_MIN)
    @Suppress("MemberVisibilityCanBePrivate")
    val pageMax: Int = jsonObject.optInt(KEY_PAGE_MAX)
    val hotSpots: List<HotSpot>? =
        jsonObject.optJSONArray(KEY_HOTSPOTS)?.map { HotSpot(it as JSONObject, pageMin, pageMax) }

    override fun toString(): String = "Page(pageMin=$pageMin, pageMax=$pageMax, hotSpots=$hotSpots)"
}
