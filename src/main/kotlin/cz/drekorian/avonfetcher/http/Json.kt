package cz.drekorian.avonfetcher.http

import kotlinx.serialization.json.Json

val json = Json {
    coerceInputValues = true
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
    useAlternativeNames = true
}
