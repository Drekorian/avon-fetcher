package cz.drekorian.avonfetcher.http.productlistmodal

import cz.drekorian.avonfetcher.I18n
import cz.drekorian.avonfetcher.http.KHttpClient
import cz.drekorian.avonfetcher.http.OK
import cz.drekorian.avonfetcher.http.Request
import mu.KotlinLogging
import org.json.JSONArray

val logger = KotlinLogging.logger { }

class ProductListModalRequest : Request() {

    companion object {
        const val BASE_URL = "https://www.avon.cz/product/productlistmodal"
        private const val PRODUCT_ID_SEPARATOR = ","
        private val CONTENT_REGEX = """_ProductListModal_ProductList=(.*)""".toRegex()
    }

    fun send(productIds: Collection<String>): ProductListModalResponse? {
        val joinedProductIds = productIds.joinToString(separator = PRODUCT_ID_SEPARATOR)
        val response = KHttpClient.get(url = "$BASE_URL/$joinedProductIds")

        if (!checkStatusCode(response, logger, I18n.get("product_list_modal_request_error"))) {
            return null
        }

        val matchResult: MatchResult? = CONTENT_REGEX.find(response.text)
        if (matchResult == null) {
            logger.error(I18n.get("product_list_modal_request_values_missing"))
            return null
        }

        val match: MatchGroup?  = matchResult.groups.last()
        if (match == null) {
            logger.error { I18n.get("product_list_modal_request_no_match") }
            return null
        }

        return ProductListModalResponse(JSONArray(match.value))
    }
}
