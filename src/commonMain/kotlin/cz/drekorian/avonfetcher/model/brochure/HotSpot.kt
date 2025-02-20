package cz.drekorian.avonfetcher.model.brochure

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class HotSpot(
    @JsonNames("Products") val products: List<Product>? = null,
)
