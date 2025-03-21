package cz.drekorian.avonfetcher

import cz.drekorian.avonfetcher.flow.MasterFlow
import mu.KLogger
import mu.KotlinLogging

private const val ALLOWED_CAMPAIGN_LENGTH = 6

internal expect val platformUsage: String

internal lateinit var logger: KLogger

fun main(args: Array<String>) {
    if (args.contains("debug")) {
        enableDebugLogging()
    }
    logger = KotlinLogging.logger("main")

    if (args.isEmpty()) {
        logger.errorI18n(platformUsage)
        return
    }

    val campaignId = args[0]
    if (!validateCampaignId(campaignId)) {
        return
    }

    MasterFlow().execute(campaignId)
}

private fun validateCampaignId(campaignId: String?): Boolean {
    if (campaignId == null) {
        logger.errorI18n("invalid_campaign_id_null")
        return false
    }

    val length = campaignId.length
    if (length != ALLOWED_CAMPAIGN_LENGTH) {
        logger.errorI18n("invalid_campaign_length", length)
        return false
    }

    return campaignId.all { it.isDigit() }
}
/**
 * Enables debug-level logs.
 */
internal expect fun enableDebugLogging()

