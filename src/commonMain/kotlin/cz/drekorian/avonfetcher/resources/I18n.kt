package cz.drekorian.avonfetcher.resources

import cz.drekorian.avonfetcher.multiplatform.util.defaultLocale

/**
 * This object handles the I18n of error messages.
 *
 * @author Marek Osvald
 */
object I18n {
    private val resources: Resources by lazy {
        when (defaultLocale.languageCode) {
            "cs" -> ResourcesCz
            else -> ResourcesEn
        }
    }

    /**
     * Returns the localised string value for given key.
     *
     * @param key I18n key to retrieve
     * @return internationalized string from a resource bundle
     */
    operator fun get(key: String) : String = resources[key]
}

/**
 * A convenience method for [I18n.get].
 *
 * @param key I18n key to retrieve
 * @return internationalized string from a resource bundle
 */
fun i18n(key: String) = I18n[key]
