package io.github.aleksandersh.mysocialnetworkphotos.data.storage

import android.net.Uri
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FriendsHttpDatasource {

    companion object {

        private const val METHOD_GET = "GET"
        private const val TIMEOUT_CONNECT = 3_000
        private const val TIMEOUT_READ = 3_000
    }

    fun getFriends(): List<Friend> {
//        val uri = Uri.Builder()
//            .path("")
//            .build()
//        val url = URL("")
//        val connection = URL("").openConnection() as HttpURLConnection
//        try {
//            connection.requestMethod = METHOD_GET
//            connection.connectTimeout = TIMEOUT_CONNECT
//            connection.readTimeout = TIMEOUT_READ
//            connection.connect()
//
//            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
//                throw IOException()
//            }
//
//
//        } finally {
//            connection.disconnect()
//        }
        return emptyList()
    }

    private fun readStream(stream: InputStream) {

    }
}