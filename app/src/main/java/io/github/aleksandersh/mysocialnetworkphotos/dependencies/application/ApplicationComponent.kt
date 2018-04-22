package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application

import android.content.Context
import android.content.SharedPreferences
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.AuthorizationHolderImpl
import io.github.aleksandersh.mysocialnetworkphotos.data.storage.SessionStorage
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationHolder
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

    val authorizationHolder: AuthorizationHolder by lazy { AuthorizationHolderImpl(sessionStorage) }
    private val sessionStorage: SessionStorage by lazy { SessionStorage(sessionPreferences) }
    private val sessionPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SESSION_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}