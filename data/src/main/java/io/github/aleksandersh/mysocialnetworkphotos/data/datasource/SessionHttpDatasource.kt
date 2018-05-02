package io.github.aleksandersh.mysocialnetworkphotos.data.datasource

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ResponseErrorHandler
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception.ApiException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.SessionHolder
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class SessionHttpDatasource(
    private val httpClient: HttpClient,
    private val responseErrorHandler: ResponseErrorHandler,
    private val sessionHolder: SessionHolder
) {

    companion object {

        private const val HOST = BuildConfig.API_CONTENT_HOST
        private const val CHECK_TOKEN_METHOD = "secure.checkToken"
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_API_VERSION = "v"
        private const val PARAM_TOKEN = "token"
    }

    fun checkSession(): Boolean {
        val session = sessionHolder.getCurrentSession()
        if (session != null) {
            val currentDate = Date()
            if (session.expires.before(currentDate)) {
                sessionHolder.invalidateSession()
                return false
            }

            val parameters = mapOf(
                PARAM_ACCESS_TOKEN to BuildConfig.API_SERVICE_TOKEN,
                PARAM_API_VERSION to BuildConfig.API_VERSION,
                PARAM_TOKEN to session.token
            )

            return httpClient
                .makeRequest(HttpClient.METHOD_GET, HOST + CHECK_TOKEN_METHOD, parameters)
                .let(::toJson)
                .let(::checkForError)
                ?.let(::parseCheckTokenJson)
                    ?: false
        }

        return false
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

    private fun checkForError(json: JSONObject): JSONObject? {
        return try {
            responseErrorHandler.checkForError(json)
        } catch (e: ApiException) {
            sessionHolder.invalidateSession()
            null
        }
    }

    private fun parseCheckTokenJson(json: JSONObject): Boolean {
        try {
            val result = json
                .getJSONObject("response")
                .getInt("success")
            return result == 1
        } catch (exception: JSONException) {
            throw ResponseParsingException(
                "Error occurred while parsing token check response",
                exception
            )
        }
    }
}