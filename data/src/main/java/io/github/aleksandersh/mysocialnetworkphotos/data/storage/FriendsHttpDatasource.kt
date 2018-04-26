package io.github.aleksandersh.mysocialnetworkphotos.data.storage

import io.github.aleksandersh.mysocialnetworkphotos.data.network.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import java.net.URL

class FriendsHttpDatasource(private val httpClient: HttpClient) {

    fun getFriends(): List<Friend> {
        httpClient.makeRequest(URL(""), HttpClient.METHOD_GET)
        return emptyList()
    }
}