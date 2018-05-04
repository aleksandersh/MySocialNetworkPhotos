package io.github.aleksandersh.mysocialnetworkphotos.data.datasource

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ResponseErrorHandler
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception.AuthenticationException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.MissingSessionException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.SessionHolder
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.PhotoInfo
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class PhotoInfoHttpDatasource(
    private val httpClient: HttpClient,
    private val responseErrorHandler: ResponseErrorHandler,
    private val sessionHolder: SessionHolder
) {

    companion object {

        private const val HOST = BuildConfig.API_CONTENT_HOST
        private const val PHOTOS_GET_METHOD = "photos.getById"
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_API_VERSION = "v"
        private const val PARAM_PHOTOS = "photos"
        private const val PARAM_EXTENDED = "extended"
    }

    fun getPhotoInfo(photoId: String): PhotoInfo {
        val parameters = mapOf(
            PARAM_ACCESS_TOKEN to getToken(),
            PARAM_API_VERSION to BuildConfig.API_VERSION,
            PARAM_PHOTOS to photoId,
            PARAM_EXTENDED to "1"
        )

        return httpClient.makeRequest(HttpClient.METHOD_GET, HOST + PHOTOS_GET_METHOD, parameters)
            .let(::toJson)
            .let(::checkForError)
            .let(::parsePhotoInfoJson)
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

    private fun checkForError(json: JSONObject): JSONObject {
        return try {
            responseErrorHandler.checkForError(json)
        } catch (exception: AuthenticationException) {
            sessionHolder.invalidateSession()
            throw exception
        }
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