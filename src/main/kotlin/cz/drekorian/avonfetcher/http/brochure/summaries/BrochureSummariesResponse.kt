package cz.drekorian.avonfetcher.http.brochure.summaries

import cz.drekorian.avonfetcher.model.brochure.Folder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class BrochureSummariesResponse(
    @JsonNames("Data") val data: List<Folder>,
)
