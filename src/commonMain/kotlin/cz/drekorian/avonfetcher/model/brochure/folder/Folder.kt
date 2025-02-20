package cz.drekorian.avonfetcher.model.brochure.folder

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Folder(
    @JsonNames("Data") val data: String
)
