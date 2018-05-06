package io.github.aleksandersh.mysocialnetworkphotos.data.network.api

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception.AuthenticationException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.MissingSessionException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.exception.ResponseParsingException
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.SessionHolder
import org.json.JSONException
import org.json.JSONObject

class ApiContentHttpClient(
    private val httpClient: HttpClient,
    private val responseErrorHandler: ResponseErrorHandler,
    private val sessionHolder: SessionHolder
) {

    companion object {

        private const val HOST = BuildConfig.API_CONTENT_HOST
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_API_VERSION = "v"
    }

    fun makeRequest(
        method: String,
        path: String,
        parameters: Map<String, String>
    ): JSONObject {
        val resultPath = HOST + path
        val resultParameters = getAdditionalParameters(parameters)

        return httpClient.makeRequest(method, resultPath, resultParameters)
            .let(::toJson)
            .let(::checkForError)
    }

    private fun getAdditionalParameters(parameters: Map<String, String>): Map<String, String> {
        val additional = LinkedHashMap<String, String>()
        if (PARAM_ACCESS_TOKEN !in parameters) {
            additional[PARAM_ACCESS_TOKEN] = getToken()
        }
        if (PARAM_API_VERSION !in parameters) {
            additional[PARAM_API_VERSION] = BuildConfig.API_VERSION
        }
        additional.putAll(parameters)
        return if (additional.isNotEmpty()) {
            additional.apply { putAll(parameters) }
        } else {
            parameters
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

    private fun checkForError(json: JSONObject): JSONObject {
        return try {
            responseErrorHandler.checkForError(json)
        } catch (exception: AuthenticationException) {
            sessionHolder.invalidateSession()
            throw exception
        }
    }
}