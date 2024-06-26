package cz.drekorian.avonfetcher.model.brochure

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Page(
    @JsonNames("PageMax") val pageMax: Int? = null,
    @JsonNames("PageMin") val pageMin: Int? = null,
    @JsonNames("Hotspots") val hotSpots: List<HotSpot>? = null,
)
