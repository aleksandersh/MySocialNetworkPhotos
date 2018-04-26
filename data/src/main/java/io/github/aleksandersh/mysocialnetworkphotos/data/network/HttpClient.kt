package io.github.aleksandersh.mysocialnetworkphotos.data.network

import java.net.URL

interface HttpClient {

    companion object {

        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
    }

    fun makeRequest(url: URL, method: String): String
}