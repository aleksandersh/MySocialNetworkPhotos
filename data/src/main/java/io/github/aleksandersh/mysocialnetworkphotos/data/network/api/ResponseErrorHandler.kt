package io.github.aleksandersh.mysocialnetworkphotos.data.network.api

import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception.ApiException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception.AuthenticationException
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.exception.PermissionDeniedException
import org.json.JSONObject

class ResponseErrorHandler {

    companion object {

        private const val CODE_AUTHORIZATION_FAILED = 5
        private const val CODE_PERMISSION_DENIED = 7
        private const val CODE_ACCESS_DENIED = 15
        private const val CODE_PAGE_BLOCKED = 18
    }

    fun checkForError(json: JSONObject): JSONObject {
        val errorBody = json.optJSONObject("error") ?: return json

        val errorCode = errorBody.optInt("error_code")
        val errorMessage = errorBody.optString("error_msg")

        when (errorCode) {
            CODE_PERMISSION_DENIED,
            CODE_ACCESS_DENIED -> throw PermissionDeniedException(errorCode, errorMessage)
            CODE_AUTHORIZATION_FAILED,
            CODE_PAGE_BLOCKED -> throw AuthenticationException(errorCode, errorMessage)
        }

        throw ApiException(errorCode, errorMessage)
    }
}