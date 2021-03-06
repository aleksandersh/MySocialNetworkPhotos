package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application

import android.content.Context
import android.content.SharedPreferences
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ApiContentHttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.api.ResponseErrorHandler
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.CoreHttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.HttpClient
import io.github.aleksandersh.mysocialnetworkphotos.data.network.httpclient.UrlBuilder
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.SessionHolder
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.SessionHolderImpl
import io.github.aleksandersh.mysocialnetworkphotos.data.storage.SessionStorage
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProviderImpl
import io.github.aleksandersh.simplemvp.PresenterProvider

class ApplicationComponent(val context: Context) {

    companion object {

        private const val SESSION_PREFERENCES_NAME = "session_properties"
    }

    val resourceManager: ResourceManager by lazy { ResourceManager(context) }
    val schedulersProvider: SchedulersProvider by lazy { SchedulersProviderImpl() }
    val presenterProvider: PresenterProvider by lazy { PresenterProvider() }
    val sessionHolder: SessionHolder by lazy { SessionHolderImpl(sessionStorage) }
    val httpClient: HttpClient by lazy { CoreHttpClient(urlBuilder) }
    val responseErrorHandler: ResponseErrorHandler by lazy { ResponseErrorHandler() }
    val apiContentHttpClient: ApiContentHttpClient by lazy {
        ApiContentHttpClient(httpClient, responseErrorHandler, sessionHolder)
    }
    val urlBuilder: UrlBuilder by lazy { UrlBuilder() }

    private val sessionStorage: SessionStorage by lazy { SessionStorage(sessionPreferences) }
    private val sessionPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SESSION_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}