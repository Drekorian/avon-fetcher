package cz.drekorian.avonfetcher

const val CAMPAIGN_SEPARATOR = "/"

fun getReadableCampaignId(campaignId: String) =
    "${campaignId.substring(4, 6)}$CAMPAIGN_SEPARATOR${campaignId.substring(0, 4)}"
