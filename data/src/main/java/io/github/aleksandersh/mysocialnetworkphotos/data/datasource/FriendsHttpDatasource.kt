package io.github.aleksandersh.mysocialnetworkphotos.data.datasource

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ResponseErrorHandler
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.MissingSessionException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.data.utils.mapJSONObject
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.FriendsResult
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionHolder
import org.json.JSONException
import org.json.JSONObject

class FriendsHttpDatasource(
    private val httpClient: HttpClient,
    private val responseErrorHandler: ResponseErrorHandler,
    private val sessionHolder: SessionHolder
) {

    companion object {

        private const val HOST = BuildConfig.API_CONTENT_HOST
        private const val FRIENDS_GET_METHOD = "friends.get"
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_API_VERSION = "v"
        private const val PARAM_ORDER = "order"
        private const val PARAM_FIELDS = "fields"
        private const val PARAM_OFFSET = "offset"
        private const val PARAM_COUNT = "count"
    }

    fun getFriends(offset: Int, count: Int): FriendsResult {
        val parameters = mapOf(
            PARAM_ACCESS_TOKEN to getToken(),
            PARAM_ORDER to "hints",
            PARAM_FIELDS to "photo_100, photo_max_orig",
            PARAM_OFFSET to offset.toString(),
            PARAM_COUNT to count.toString(),
            PARAM_API_VERSION to BuildConfig.API_VERSION
        )

        return httpClient.makeRequest(HttpClient.METHOD_GET, HOST + FRIENDS_GET_METHOD, parameters)
            .let(::toJson)
            .let(responseErrorHandler::checkForError)
            .let(::parseFriendsJson)
            .let {
                val contentFinished = (offset + count) >= it.second
                FriendsResult(it.first, it.second, contentFinished)
            }
    }

    private fun getToken(): String {
        return sessionHolder.getCurrentSession()?.token
                ?: throw MissingSessionException()
    }

    private fun toJson(response: ByteArray): JSONObject {
        try {
            return JSONObject(String(response))
        } catch (exception: JSONException) {
            throw ResponseParsingException(
                "Error occurred while parsing json",
                exception
            )
        }
    }

    private fun parseFriendsJson(json: JSONObject): Pair<List<Friend>, Int> {
        try {
            return json
                .getJSONObject("response")
                .let {
                    val friends = it.getJSONArray("items")
                        .mapJSONObject {
                            with(it) {
                                val id = getLong("id")
                                val firstName = getString("first_name")
                                val lastName = getString("last_name")
                                val smallPhotoUrl = getString("photo_100")
                                val bigPhotoUrl = getString("photo_max_orig")
                                Friend(id, firstName, lastName, smallPhotoUrl, bigPhotoUrl)
                            }
                        }
                    val totalCount = it.getInt("count")
                    Pair(friends, totalCount)
                }
        } catch (exception: JSONException) {
            throw ResponseParsingException(
                "Error occurred while parsing friends list",
                exception
            )
        }
    }
}