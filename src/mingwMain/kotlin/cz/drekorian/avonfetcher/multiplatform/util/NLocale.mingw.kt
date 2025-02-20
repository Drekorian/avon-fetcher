package cz.drekorian.avonfetcher.multiplatform.util

import platform.windows.GetUserDefaultLangID

private const val LANG_ID_CZECH = 1029

/**
 * Returns user selected locale.
 *
 * @return user selected locale
 * @author Marek Osvald
 */
actual val defaultLocale: NLocale
    get() = when (GetUserDefaultLangID().toInt()) {
        LANG_ID_CZECH -> NLocale(languageCode = "cs", region = "CZ")
        else -> NLocale(languageCode = "en", region = "GB")
    }
