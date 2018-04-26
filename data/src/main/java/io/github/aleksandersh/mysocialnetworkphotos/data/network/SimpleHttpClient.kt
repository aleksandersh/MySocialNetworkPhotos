package io.github.aleksandersh.mysocialnetworkphotos.data.network

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class SimpleHttpClient(
    private val connectTimeout: Int = DEFAULT_CONNECT_TIMEOUT,
    private val readTimeout: Int = DEFAULT_READ_TIMEOUT,
    private val logger: NetworkLogger
) : HttpClient {

    companion object {

        private const val DEFAULT_CONNECT_TIMEOUT = 3_000
        private const val DEFAULT_READ_TIMEOUT = 3_000

        private const val TAG = "SimpleHttpClient"
    }

    override fun makeRequest(url: URL, method: String): String {
        Log.d(TAG, "sending: $method / $url")

        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = method
            connection.connectTimeout = connectTimeout
            connection.readTimeout = readTimeout
            connection.connect()

            val code = connection.responseCode
            val message = connection.responseMessage
            if (code != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Error: $code $message")
                throw IOException()
            }

            Log.d(TAG, "Done: $code $message")

            val data = connection.inputStream.use(::readStream)

            Log.d(TAG, "Data: $data")

            return data
        } finally {
            connection.disconnect()
        }
    }

    private fun readStream(stream: InputStream): String {
        return stream.bufferedReader().readText()
    }
}