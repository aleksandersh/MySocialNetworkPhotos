package io.github.aleksandersh.mysocialnetworkphotos.data.datasource

import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ApiContentHttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.data.utils.mapJSONObject
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.FriendsResult
import org.json.JSONException
import org.json.JSONObject

class FriendsHttpDatasource(private val httpClient: ApiContentHttpClient) {

    companion object {

        private const val FRIENDS_GET_METHOD = "friends.get"
        private const val PARAM_ORDER = "order"
        private const val PARAM_FIELDS = "fields"
        private const val PARAM_OFFSET = "offset"
        private const val PARAM_COUNT = "count"
    }

    fun getFriends(offset: Int, count: Int): FriendsResult {
        val parameters = mapOf(
            PARAM_ORDER to "hints",
            PARAM_FIELDS to "photo_100, photo_max, photo_id",
            PARAM_OFFSET to offset.toString(),
            PARAM_COUNT to count.toString()
        )

        return httpClient.makeRequest(HttpClient.METHOD_GET, FRIENDS_GET_METHOD, parameters)
            .let(::parseFriendsJson)
            .let {
                val contentFinished = (offset + count) >= it.second
                FriendsResult(it.first, it.second, contentFinished)
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
                                Friend(
                                    id = getLong("id"),
                                    firstName = getString("first_name"),
                                    lastName = getString("last_name"),
                                    smallPhotoUrl = getString("photo_100"),
                                    bigPhotoUrl = getString("photo_max"),
                                    photoId = optString("photo_id")
                                )
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