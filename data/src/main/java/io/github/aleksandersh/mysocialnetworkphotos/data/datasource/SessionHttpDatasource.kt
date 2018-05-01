package io.github.aleksandersh.mysocialnetworkphotos.data.datasource

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionHolder
import org.json.JSONException
import org.json.JSONObject

class SessionHttpDatasource(
    private val httpClient: HttpClient,
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
            val parameters = mapOf(
                PARAM_ACCESS_TOKEN to BuildConfig.API_SERVICE_TOKEN,
                PARAM_API_VERSION to BuildConfig.API_VERSION,
                PARAM_TOKEN to session.token
            )

            return httpClient
                .makeRequest(HttpClient.METHOD_GET, HOST + CHECK_TOKEN_METHOD, parameters)
                .let { String(it) }
                .let(::parseCheckTokenResponse)
        }

        return false
    }

    private fun parseCheckTokenResponse(json: String): Boolean {
        try {
            val result = JSONObject(json)
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