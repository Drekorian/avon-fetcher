package cz.drekorian.avonfetcher.http.brochure.folder

import cz.drekorian.avonfetcher.http.INDENT_FACTOR
import cz.drekorian.avonfetcher.model.brochure.Page
import cz.drekorian.avonfetcher.model.brochure.Product
import org.json.JSONObject

class BrochureFolderResponse(data: JSONObject) {

    companion object {
        private const val KEY_DATA = "Data"
        private const val KEY_PAGES = "Pages"
    }

    val products: List<Product> = with(data) {
        val rawData = getString(KEY_DATA)
        val dataObject = JSONObject(rawData)
        logger.debug(dataObject.toString(INDENT_FACTOR))

        val result = mutableListOf<Product>()
        dataObject.getJSONArray(KEY_PAGES).forEach { pageRaw ->
            val pageObject: JSONObject = pageRaw as JSONObject
            val page = Page(pageObject)

            page.hotSpots?.forEach { hotSpot ->
                hotSpot.products?.forEach { product ->
                    result.add(product)
                }
            }
        }

        // return
        result.toList()
    }
}
