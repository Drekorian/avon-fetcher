package cz.drekorian.avonfetcher.resources

object ResourcesCz : Resources {

    @Suppress("SpellCheckingInspection")
    private val keys = mapOf(
        "usage_jvm" to "Pouziti: : java -jar avon-mobile-fetcher-all-%s.jar YYYYMM [debug]",
        "usage_macos" to "Pouziti: ./avon-mobile-fetcher-%s.kexe YYYYMM [debug]",
        "usage_mingw" to "Pouziti: avon-mobile-fetcher-%s.exe YYYYMM [debug]",
        "brochure_folder_request_ok" to "Slozka katalogu %s byla uspesne nactena",
        "brochure_folder_request_error" to "Stazeni slozky katalogu %s selhalo!",
        "brochure_summaries_request_ok" to "Souhrny katalogu uspesne stazeny",
        "brochure_summaries_request_error" to "Stazeni souhrnu katalogu selhalo!",
        "master_flow_summaries_response_null" to "Stazeni souhrnu katalogu selhalo. Katalog jeste nebyl vydan?",
        "master_flow_brochure_summary" to "Katalog pro kampan %s obsahuje %s slozek.",
        "master_flow_brochure_folder_debug_log" to "Nazev slozky: %s, pocet stran: %s, folder: %s, published: %s",
        "master_flow_brochure_folder_response" to "Nacitani slozky katalogu %s selhalo!",
        "master_flow_product_list_modal_response" to "Stahovani modalniho seznamu produktu pro produkty %s selhalo! Pokousim se o nahradni reseni.",
        "master_flow_product_fallback_failed" to "Nahradni reseni pro produkt %s selhalo!",
        "product_list_modal_request_error" to "Stahovani modalniho seznamu produktu selhalo!",
        "product_list_modal_request_values_missing" to "Stahovani modalniho seznamu produktu selhalo, seznam nenalezen!",
        "product_list_modal_request_no_match" to "Angularove vazby nenalezeny!",
    )

    override operator fun get(key: String) = keys[key] ?: super.get(key)
}
