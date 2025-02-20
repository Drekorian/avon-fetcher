package cz.drekorian.avonfetcher.resources

object ResourcesEn : Resources {

    @Suppress("SpellCheckingInspection")
    private val keys = mapOf(
        "usage_jvm" to "Usage: java -jar avon-mobile-fetcher-all-%s.jar YYYYMM [debug]",
        "usage_macos" to "Usage: ./avon-mobile-fetcher-%s.kexe YYYYMM [debug]",
        "usage_mingw" to "Usage: avon-mobile-fetcher-%s.exe YYYYMM [debug]",
        "brochure_folder_request_ok" to "Brochure folder %s successfully fetched",
        "brochure_folder_request_error" to "Brochure folder %s fetching failed!",
        "brochure_summaries_request_ok" to "Brochure summaries successfully fetched",
        "brochure_summaries_request_error" to "Brochure summaries fetching failed!",
        "master_flow_summaries_response_null" to "Brochure summary does not exists. Brochure not published yet?",
        "master_flow_brochure_summary" to "The brochure for campaign %s contains %s folders.",
        "master_flow_brochure_folder_debug_log" to "Folder title: %s, page count: %s, folder: %s, published: %s",
        "master_flow_brochure_folder_response" to "Fetching brochure folder %s has failed!",
        "master_flow_product_list_modal_response" to "Fetching modal product list for products %s has failed! Attempting fallback.",
        "master_flow_product_fallback_failed" to "Fallback call for product %s has failed!",
        "product_list_modal_request_error" to "Product list modal fetching failed!",
        "product_list_modal_request_values_missing" to "Product list modal fetching failed, match not found!",
        "product_list_modal_request_no_match" to "Angular bindings not found!",
    )

    override operator fun get(key: String) = keys[key] ?: super.get(key)
}
