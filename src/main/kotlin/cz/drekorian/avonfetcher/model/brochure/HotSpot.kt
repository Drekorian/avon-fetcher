package cz.drekorian.avonfetcher.model.brochure

import org.json.JSONObject

class HotSpot(jsonObject: JSONObject, val pageMin: Int, val pageMax: Int) {

    companion object {
        private const val KEY_PRODUCTS = "Products"
    }

    val products: List<Product>? =
        jsonObject.optJSONArray(KEY_PRODUCTS)?.map { Product(it as JSONObject, pageMin, pageMax) }

    override fun toString(): String = "HotSpot(pageMin=$pageMin, pageMax=$pageMax, products=$products)"
}
