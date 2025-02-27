package cz.drekorian.avonfetcher.model.brochure

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Folder(
    @JsonNames("Title") val title: String = "",
    @JsonNames("PageCount") val pageCount: Int = 0,
    @JsonNames("Folder") val folder: String = "",
    @JsonNames("Published") val isPublished: Boolean = false,
)
