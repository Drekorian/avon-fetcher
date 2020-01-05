package cz.drekorian.avonfetcher.http.productlistmodal

import cz.drekorian.avonfetcher.model.productlist.Product
import org.json.JSONArray
import org.json.JSONObject

class ProductListModalResponse(data: JSONArray) {

    val products = data.map {
        val jsonObject = it as JSONObject
        Product(jsonObject)
    }.groupBy { it.id }
}
