package io.github.aleksandersh.mysocialnetworkphotos.data.datasource

import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ApiContentHttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class PhotoInfoHttpDatasource(private val httpClient: ApiContentHttpClient) {

    companion object {

        private const val PHOTOS_GET_METHOD = "photos.getById"
        private const val PARAM_PHOTOS = "photos"
        private const val PARAM_EXTENDED = "extended"
    }

    fun getPhotoInfo(photoId: String): PhotoInfo {
        val parameters = mapOf(
            PARAM_PHOTOS to photoId,
            PARAM_EXTENDED to "1"
        )

        return httpClient.makeRequest(HttpClient.METHOD_GET, PHOTOS_GET_METHOD, parameters)
            .let(::parsePhotoInfoJson)
    }

    private fun parsePhotoInfoJson(json: JSONObject): PhotoInfo {
        try {
            return json
                .getJSONArray("response")
                .getJSONObject(0)
                .let {
                    with(it) {
                        val date = getLong("date")
                            .let { TimeUnit.SECONDS.toMillis(it) }
                            .let(::Date)
                        PhotoInfo(
                            id = getLong("id"),
                            ownerId = getLong("owner_id"),
                            albumId = getLong("album_id"),
                            text = getString("text"),
                            date = date,
                            likeCount = getJSONObject("likes").getInt("count"),
                            commentCount = getJSONObject("comments").getInt("count")
                        )
                    }
                }
        } catch (exception: JSONException) {
            throw ResponseParsingException(
                "Error occurred while parsing photo info",
                exception
            )
        }
    }
}