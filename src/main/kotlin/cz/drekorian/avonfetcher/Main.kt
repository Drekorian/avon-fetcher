@file:JvmName("Main")

package cz.drekorian.avonfetcher

import cz.drekorian.avonfetcher.flow.MasterFlow
import mu.KLogger
import mu.KotlinLogging
import org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY
import java.util.Locale
import java.util.ResourceBundle

private const val ALLOWED_CAMPAIGN_LENGTH = 6
private const val I18N_RESOURCE_BUNDLE = "locale"

internal lateinit var logger: KLogger

fun main(args: Array<String>) {
    if (args.contains("debug")) {
        System.setProperty(DEFAULT_LOG_LEVEL_KEY, "TRACE")
        logger = KotlinLogging.logger("main")
    }

    // load up the I18n singleton
    I18n.resourceBundle = ResourceBundle.getBundle(I18N_RESOURCE_BUNDLE, Locale.UK)

    if (args.isEmpty()) {
//        logger.error { I18n.get("usage") }
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
//        logger.error { I18n.get("invalid_campaign_id_null") }
        return false
    }

    val length = campaignId.length
    if (length != ALLOWED_CAMPAIGN_LENGTH) {
//        logger.error { I18n.get("invalid_campaign_length") + " $length" }
        return false
    }

    return campaignId.all { it.isDigit() }
}
