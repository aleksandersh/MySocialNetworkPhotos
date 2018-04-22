package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.BuildConfig
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.AuthorizationProperties
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationHolder
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationRepository
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class AuthorizationRepositoryImpl(
    private val authorizationHolder: AuthorizationHolder
) : AuthorizationRepository {

    companion object {

        private const val HOST = BuildConfig.API_AUTH_HOST
        private const val AUTH_PAGE = "authorize"
        private const val BLANK_PAGE = "blank.html"

        private const val CLIENT_ID = BuildConfig.API_CLIENT_ID
        private const val DISPLAY = "mobile"
        private const val API_VERSION = BuildConfig.API_VERSION
        private const val RESPONSE_TYPE = "token"
        private const val STATE = "some_state"

        private const val SCOPE_FRIENDS = 2
        private const val SCOPE_PHOTOS = 4
        private const val SCOPE = SCOPE_FRIENDS or SCOPE_PHOTOS
    }

    private val properties: AuthorizationProperties by lazy(::setupAuthorizationProperties)

    private val signInRedirectPattern: Pattern by lazy {
        Pattern.compile("\\Q${properties.redirectUri}\\E#access_token=([a-z0-9]+)&expires_in=(\\d+)&user_id=(\\d+)&state=${properties.state}")
    }
    private val errorPattern: Pattern by lazy {
        Pattern.compile("\\Q${properties.redirectUri}\\E#error=access_denied&error_reason=user_denied.*")
    }

    override fun getAuthorizationProperties(): AuthorizationProperties {
        return properties
    }

    override fun checkUrlHandling(url: String): Boolean {
        return url.startsWith(properties.redirectUri)
    }

    override fun checkUrlForLoginAndHandleSession(url: String): Boolean {
        val signInMatcher = signInRedirectPattern.matcher(url)
        if (signInMatcher.find()) {
            val token = signInMatcher.group(1)
            val expires = signInMatcher.group(2).toLong()
            val userId = signInMatcher.group(3).toLong()

            val currentTime = System.currentTimeMillis()
            val expiresTime = TimeUnit.SECONDS.toMillis(expires)
            val expiresDate = Date(expiresTime + currentTime)

            val session = UserSession(userId, token, expiresDate)
            authorizationHolder.saveNewSession(session)

            return true
        }

        return false
    }

    override fun checkUrlForCancel(url: String): Boolean {
        return errorPattern.matcher(url).matches()
    }

    private fun setupAuthorizationProperties(): AuthorizationProperties {
        return AuthorizationProperties(
            host = HOST,
            clientId = CLIENT_ID,
            apiVersion = API_VERSION,
            state = STATE,
            authorizationUri = "$HOST$AUTH_PAGE",
            redirectUri = "$HOST$BLANK_PAGE",
            authorizationQuery = getAuthorizationQuery()
        )
    }

    private fun getAuthorizationQuery(): String {
        return """$HOST$AUTH_PAGE
            |?client_id=$CLIENT_ID
            |&display=$DISPLAY
            |&v=$API_VERSION
            |&response_type=$RESPONSE_TYPE
            |&scope=${SCOPE.toString(2)}
            |&redirect_uri=$HOST$BLANK_PAGE"""
            .trimMargin()
    }
}