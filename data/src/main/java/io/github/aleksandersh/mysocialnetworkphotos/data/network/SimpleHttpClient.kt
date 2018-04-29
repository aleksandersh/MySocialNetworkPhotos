package io.github.aleksandersh.mysocialnetworkphotos.data.network

import android.net.Uri
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class SimpleHttpClient(
    private val host: String,
    private val connectTimeout: Int = DEFAULT_CONNECT_TIMEOUT,
    private val readTimeout: Int = DEFAULT_READ_TIMEOUT
) : HttpClient {

    companion object {

        private const val DEFAULT_CONNECT_TIMEOUT = 3_000
        private const val DEFAULT_READ_TIMEOUT = 3_000
    }

    override fun makeRequest(
        method: String,
        path: String,
        parameters: Map<String, String>
    ): String {
        val url = getUrl(path, parameters)
        return makeRequest(url, method)
    }

    override fun makeRequest(url: URL, method: String): String {
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = method
            connection.connectTimeout = connectTimeout
            connection.readTimeout = readTimeout
            connection.connect()

            val code = connection.responseCode
            val message = connection.responseMessage
            if (code != HttpURLConnection.HTTP_OK) {
                throw IOException()
            }

            return connection.inputStream.use(::readStream)
        } finally {
            connection.disconnect()
        }
    }

    private fun getUrl(path: String, parameters: Map<String, String>): URL {
        val builder = Uri.Builder()
            .encodedPath(host)
            .appendEncodedPath(path)

        parameters.forEach { key, value ->
            builder.appendQueryParameter(key, value)
        }

        val uri = builder.build().toString()
        return URL(uri)
    }

    private fun readStream(stream: InputStream): String {
        return stream.bufferedReader().readText()
    }
}