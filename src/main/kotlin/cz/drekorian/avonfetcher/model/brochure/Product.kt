package cz.drekorian.avonfetcher.model.brochure

import org.json.JSONObject

class Product(jsonObject: JSONObject, val pageMin: Int, val pageMax: Int) {

    companion object {
        private const val KEY_PRODUCT_NAME = "ProductName"
        private const val KEY_VALID = "Valid"
        private const val KEY_PRODUCT_ID = "ProductId"
        private const val KEY_LINE_NUMBER = "LineNumber"
    }

    val productName: String = jsonObject.optString(KEY_PRODUCT_NAME)
    val valid: Boolean = jsonObject.optBoolean(KEY_VALID)
    val productId: String = jsonObject.optString(KEY_PRODUCT_ID)
    val lineNumber: String = jsonObject.optString(KEY_LINE_NUMBER)

    override fun toString(): String = "Product(pageMin=$pageMin, pageMax=$pageMax, productName='$productName'," +
            "valid=$valid, productId='$productId'," + "lineNumber='$lineNumber')"
}
