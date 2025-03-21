package cz.drekorian.avonfetcher

actual fun String.nFormat(vararg args: String): String = format(*args)
