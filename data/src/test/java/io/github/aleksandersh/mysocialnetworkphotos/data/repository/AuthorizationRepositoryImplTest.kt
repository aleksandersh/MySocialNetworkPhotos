package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import com.nhaarman.mockito_kotlin.*
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.UrlBuilder
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationRepository
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyMap

class AuthorizationRepositoryImplTest {

    private lateinit var urlBuilder: UrlBuilder
    private lateinit var sessionHolder: SessionHolder
    private lateinit var repository: AuthorizationRepository

    @Before
    fun setUp() {
        urlBuilder = mock()
        sessionHolder = mock()
        repository = AuthorizationRepositoryImpl(urlBuilder, sessionHolder)
    }

    @Test
    fun checkUrlForLoginAndHandleSession_match() {
        val pathAuth = "https://oauth.vk.com/authorize"
        val pathBlank = "https://oauth.vk.com/blank.html"
        val token = "533bacf01e11f55b536a565b57531ad114461ae8736d6506a3"
        val userId = 8492L
        val expiresIn = "86400"
        val url =
            "$pathBlank#access_token=$token&expires_in=$expiresIn&user_id=$userId&state=some_state"

        val expect = true

        whenever(urlBuilder.getUrl(eq(pathAuth), anyMap())).thenReturn("query")

        val actual = repository.checkUrlForLoginAndHandleSession(url)

        assert(expect == actual)
        verify(sessionHolder).saveNewSession(argThat {
            this.token == token && this.userId == userId
        })
    }

    @Test
    fun checkUrlForCancel_match() {
        val pathAuth = "https://oauth.vk.com/authorize"
        val url =
            "https://oauth.vk.com/blank.html#error=access_denied&error_reason=user_denied&error_description=User%20denied%20your%20request&state=some_state"
        val expect = true

        whenever(urlBuilder.getUrl(eq(pathAuth), anyMap())).thenReturn("query")

        val actual = repository.checkUrlForCancel(url)

        assert(expect == actual)
    }

    @Test
    fun checkUrlForCancel_notMatch() {
        val pathAuth = "https://oauth.vk.com/authorize"
        val url =
            "https://oauth.vk.com/blank.html#error=some_error&error_reason=user_denied&error_description=User%20denied%20your%20request&state=some_state"
        val expect = false

        whenever(urlBuilder.getUrl(eq(pathAuth), anyMap())).thenReturn("query")

        val actual = repository.checkUrlForCancel(url)

        assert(expect == actual)
    }
}