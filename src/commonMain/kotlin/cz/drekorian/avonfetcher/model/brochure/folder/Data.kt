package cz.drekorian.avonfetcher.model.brochure.folder

import cz.drekorian.avonfetcher.model.brochure.Page
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Data(
    @JsonNames("Pages") val pages: List<Page>,
)
