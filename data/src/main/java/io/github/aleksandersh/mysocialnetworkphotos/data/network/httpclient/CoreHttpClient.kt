package io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class CoreHttpClient(
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
    ): ByteArray {
        val url = getUrl(path, parameters)
        return makeRequest(url, method)
    }

    override fun makeRequest(url: URL, method: String): ByteArray {
        Log.d(HttpClient.TAG, "request: $method / $url")
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = method
            connection.connectTimeout = connectTimeout
            connection.readTimeout = readTimeout
            connection.connect()

            val code = connection.responseCode
            val message = connection.responseMessage
            if (code != HttpURLConnection.HTTP_OK) {
                Log.d(
                    HttpClient.TAG,
                    """error: $method $code / $url
                    $message"""
                )
                throw IOException()
            }
            Log.d(HttpClient.TAG, "success: $method $code / $url")

            return connection.inputStream.use(::readStream)
        } finally {
            connection.disconnect()
        }
    }

    private fun getUrl(path: String, parameters: Map<String, String>): URL {
        val builder = Uri.Builder()
            .encodedPath(path)

        parameters.forEach { key, value ->
            builder.appendQueryParameter(key, value)
        }

        val uri = builder.build().toString()
        return URL(uri)
    }

    private fun readStream(stream: InputStream): ByteArray {
        return stream.buffered().readBytes()
    }
}