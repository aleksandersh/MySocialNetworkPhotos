package io.github.aleksandersh.mysocialnetworkphotos.data.datasource

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ApiContentHttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception.ApiException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.SessionHolder
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class SessionHttpDatasource(
    private val httpClient: ApiContentHttpClient,
    private val sessionHolder: SessionHolder
) {

    companion object {

        private const val CHECK_TOKEN_METHOD = "secure.checkToken"
        private const val PARAM_ACCESS_TOKEN = "access_token"
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
                PARAM_TOKEN to session.token
            )

            return makeRequest(HttpClient.METHOD_GET, CHECK_TOKEN_METHOD, parameters)
                ?.let(::parseCheckTokenJson)
                    ?: false
        }

        return false
    }

    private fun makeRequest(
        method: String,
        path: String,
        parameters: Map<String, String>
    ): JSONObject? {
        return try {
            httpClient.makeRequest(method, path, parameters)
        } catch (exception: ApiException) {
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