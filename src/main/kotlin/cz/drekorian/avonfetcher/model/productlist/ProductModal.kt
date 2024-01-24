package cz.drekorian.avonfetcher.model.productlist

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class ProductModal(
    @JsonNames("Product") val product: Product,
)
