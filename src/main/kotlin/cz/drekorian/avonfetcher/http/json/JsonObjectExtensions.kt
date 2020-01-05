@file:JvmName("JsonObjectExtensions")

package cz.drekorian.avonfetcher.http.json

import org.json.JSONObject

fun JSONObject.safeGetDouble(key: String, default: Double = 0.0): Double {
    val read = optDouble(key)
    return if (!read.isNaN()) read else default
}
