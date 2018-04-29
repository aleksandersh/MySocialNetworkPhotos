package io.github.aleksandersh.mysocialnetworkphotos.data.storage

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.data.network.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.exception.MissingSessionException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.data.utils.mapJSONObject
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationHolder
import org.json.JSONException
import org.json.JSONObject

class FriendsHttpDatasource(
    private val httpClient: HttpClient,
    private val authorizationHolder: AuthorizationHolder
) {

    companion object {

        private const val FRIENDS_GET_METHOD = "friends.get"
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_ORDER = "order"
        private const val PARAM_FIELDS = "fields"
        private const val PARAM_API_VERSION = "v"
    }

    fun getFriends(): List<Friend> {
        val parameters = mapOf(
            PARAM_ACCESS_TOKEN to getToken(),
            PARAM_ORDER to "hints",
            PARAM_FIELDS to "photo_50, photo_max_orig",
            PARAM_API_VERSION to BuildConfig.API_VERSION
        )

        return httpClient.makeRequest(HttpClient.METHOD_GET, FRIENDS_GET_METHOD, parameters)
            .let(::parseFriendsResponse)
    }

    private fun getToken(): String {
        return authorizationHolder.getCurrentSession()?.token
                ?: throw MissingSessionException()
    }

    private fun parseFriendsResponse(json: String): List<Friend> {
        return try {
            JSONObject(json)
                .getJSONObject("response")
                .getJSONArray("items")
                .mapJSONObject {
                    with(it) {
                        val id = getLong("id")
                        val firstName = getString("first_name")
                        val lastName = getString("last_name")
                        val smallPhotoUrl = getString("photo_50")
                        val bigPhotoUrl = getString("photo_max_orig")
                        Friend(id, firstName, lastName, smallPhotoUrl, bigPhotoUrl)
                    }
                }
        } catch (exception: JSONException) {
            throw ResponseParsingException(
                "Error occurred while parsing friends list",
                exception
            )
        }
    }
}