package cz.drekorian.avonfetcher.multiplatform.util

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual val defaultLocale: NLocale
    get() = with(NSLocale.currentLocale) {
        countryCode?.let { countryCode ->
            NLocale(
                languageCode = languageCode,
                region = countryCode,
            )
        } ?: NLocale(
            languageCode = "en",
            region = "GB",
        )
    }
