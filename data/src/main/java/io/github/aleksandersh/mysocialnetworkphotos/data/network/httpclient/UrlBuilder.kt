package io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient

import android.net.Uri

class UrlBuilder {

    fun getUrl(path: String, parameters: Map<String, String>): String {
        val builder = Uri.Builder()
            .encodedPath(path)

        parameters.forEach { key, value ->
            builder.appendQueryParameter(key, value)
        }

        return builder.build().toString()
    }
}