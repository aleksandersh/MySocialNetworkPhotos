package io.github.aleksandersh.mysocialnetworkphotos.data.storage

import android.content.SharedPreferences
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession
import java.util.*

class SessionStorage(private val preferences: SharedPreferences) {

    companion object {

        private const val KEY_USER_ID = "user_id"
        private const val KEY_TOKEN = "token"
        private const val KEY_EXPIRES = "expires"
    }

    fun saveSession(session: UserSession) {
        preferences.edit()
            .putLong(KEY_USER_ID, session.userId)
            .putString(KEY_TOKEN, session.token)
            .putLong(KEY_EXPIRES, session.expires.time)
            .apply()
    }

    fun getSession(): UserSession? {
        val userId = preferences.getLong(KEY_USER_ID, 0)
            .takeIf { it != 0L }
                ?: return null
        val token = preferences.getString(KEY_TOKEN, null)
                ?: return null
        val expires = preferences.getLong(KEY_EXPIRES, 0)
            .takeIf { it != 0L }
            ?.let(::Date)
                ?: return null

        return UserSession(userId, token, expires)
    }

    fun clearSession() {
        preferences.edit()
            .remove(KEY_USER_ID)
            .remove(KEY_TOKEN)
            .remove(KEY_EXPIRES)
            .apply()
    }
}