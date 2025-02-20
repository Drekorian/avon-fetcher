package cz.drekorian.avonfetcher.resources

/**
 * This interface denotes the common behavior for resource classes.
 *
 * @author Marek Osvald
 */
interface Resources {

    operator fun get(key: String): String = "key_not_found$<$key>"
}