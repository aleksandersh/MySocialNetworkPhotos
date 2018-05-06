package io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient

import java.net.URL

interface HttpClient {

    companion object {

        const val TAG = "HttpClient"
        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
    }

    fun makeRequest(url: URL, method: String): ByteArray

    fun makeRequest(
        method: String,
        path: String,
        parameters: Map<String, String> = emptyMap()
    ): ByteArray
}