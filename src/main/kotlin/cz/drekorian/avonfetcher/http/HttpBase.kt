package cz.drekorian.avonfetcher.http

internal const val BASE_HOST = "www.avon.cz"
internal const val BASE_PATH_API = "api"
@Suppress("SpellCheckingInspection")
internal const val BASE_PATH_BROCHURE_API = "brochureapi"

private const val QUERY_KEY_CAMPAIGN_NUMBER = "campaignNumber"
private const val QUERY_KEY_LANGUAGE = "language"
private const val QUERY_KEY_MARKET = "market"
private const val QUERY_VALUE_LANGUAGE = "cs"
private const val QUERY_VALUE_MARKET = "CZ"

fun baseParams(campaignNumber: String): Map<String, String> = mapOf(
    QUERY_KEY_CAMPAIGN_NUMBER to campaignNumber,
    QUERY_KEY_LANGUAGE to QUERY_VALUE_LANGUAGE,
    QUERY_KEY_MARKET to QUERY_VALUE_MARKET
)
