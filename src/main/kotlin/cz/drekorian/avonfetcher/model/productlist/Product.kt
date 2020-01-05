package cz.drekorian.avonfetcher.model.productlist

import cz.drekorian.avonfetcher.http.json.safeGetDouble
import org.json.JSONObject
import org.jsoup.Jsoup

class Product(jsonObject: JSONObject) {

    companion object {
        private const val KEY_DESCRIPTION = "Description"
        private const val KEY_ID = "Id"
        private const val KEY_INGREDIENTS = "Ingredients"
        private const val KEY_IS_NEW = "IsNew"
        private const val KEY_IS_ON_SALE = "IsOnSale"
        private const val KEY_LIST_PRICE = "ListPrice"
        private const val KEY_NAME = "Name"
        private const val KEY_PRODUCT = "Product"
        private const val KEY_SALE_PRICE = "SalePrice"
        private const val KEY_UNIT_PRICE = "UnitPrice"
    }

    val id: String?
    val name: String?
    val listPrice: Double
    val salePrice: Double
    val unitPrice: Double
    val isNew: Boolean
    val isOnSale: Boolean
    val ingredients = Jsoup.parse(jsonObject.optString(KEY_INGREDIENTS)).text().replace(";", "")
    val description = Jsoup.parse(jsonObject.optString(KEY_DESCRIPTION)).text().replace(";", "")

    init {
        val productObject: JSONObject? = jsonObject.optJSONObject(KEY_PRODUCT)
        if (productObject == null) {
            id = null
            name = null
            listPrice = 0.0
            salePrice = 0.0
            unitPrice = 0.0
            isNew = false
            isOnSale = false
        } else {
            productObject.run {
                id = optString(KEY_ID)
                name = optString(KEY_NAME)
                listPrice = safeGetDouble(KEY_LIST_PRICE)
                salePrice = safeGetDouble(KEY_SALE_PRICE)
                unitPrice = safeGetDouble(KEY_UNIT_PRICE)
                isNew = optBoolean(KEY_IS_NEW)
                isOnSale = optBoolean(KEY_IS_ON_SALE)
            }
        }
    }

    override fun toString(): String {
        return "Product(id=$id, name=$name, listPrice=$listPrice, salePrice=$salePrice, unitPrice=$unitPrice," +
                "isNew=$isNew, ingredients='$ingredients', description='$description')"
    }
}
