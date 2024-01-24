package cz.drekorian.avonfetcher.model.brochure

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Product(
    @JsonNames("ProductName") val productName: String = "",
    @JsonNames("Valid") val isValid: Boolean,
    @JsonNames("ProductId") private val _productId: Int,
    @JsonNames("LineNumber") val lineNumber: String,
) {

    val productId: String
        get() = _productId.toString()
}
