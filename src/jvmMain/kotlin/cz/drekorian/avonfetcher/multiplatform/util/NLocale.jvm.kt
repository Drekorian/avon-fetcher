package cz.drekorian.avonfetcher.multiplatform.util

import java.util.Locale

actual val defaultLocale: NLocale
    get() = Locale.getDefault().toNLocale()

private fun Locale.toNLocale(): NLocale = NLocale(
    languageCode = language,
    region = country,
)
