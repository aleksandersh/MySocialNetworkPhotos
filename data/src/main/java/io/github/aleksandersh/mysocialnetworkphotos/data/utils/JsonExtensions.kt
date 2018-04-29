package io.github.aleksandersh.mysocialnetworkphotos.data.utils

import org.json.JSONArray
import org.json.JSONObject

inline fun <T> JSONArray.map(transform: (Any) -> T): List<T> {
    return List(length()) { idx ->
        transform(get(idx))
    }
}

inline fun <T> JSONArray.mapJSONObject(transform: (JSONObject) -> T): List<T> {
    return List(length()) { idx ->
        transform(getJSONObject(idx))
    }
}
