package cz.drekorian.avonfetcher.http.productlistmodal

import cz.drekorian.avonfetcher.I18n
import cz.drekorian.avonfetcher.http.BASE_HOST
import cz.drekorian.avonfetcher.http.KtorHttpClient
import cz.drekorian.avonfetcher.http.Request
import cz.drekorian.avonfetcher.http.json
import io.ktor.client.call.body
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import mu.KotlinLogging

val logger = KotlinLogging.logger { }

class ProductListModalRequest : Request() {

    companion object {
        private const val PRODUCT_ID_SEPARATOR = ","
        private val CONTENT_REGEX = """_ProductListModal_ProductList=(.*);""".toRegex()
    }

    suspend fun send(productIds: Collection<String>): ProductListModalResponse? {
        val joinedProductIds = productIds.joinToString(separator = PRODUCT_ID_SEPARATOR)
        val response = KtorHttpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_HOST
                @Suppress("SpellCheckingInspection")
                appendPathSegments("product", "productlistmodal", joinedProductIds)
            }
        }

        if (!checkStatusCode(response, logger, I18n.get("product_list_modal_request_error"))) {
            return null
        }

        val matchResult: MatchResult? = CONTENT_REGEX.find(response.body<String>())
        if (matchResult == null) {
            logger.error(I18n.get("product_list_modal_request_values_missing"))
            return null
        }

        val match: MatchGroup? = matchResult.groups.last()
        if (match == null) {
            logger.error { I18n.get("product_list_modal_request_no_match") }
            return null
        }

        return ProductListModalResponse(json.decodeFromString(match.value))
    }
}
