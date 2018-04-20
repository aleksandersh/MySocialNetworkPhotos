package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application

import android.content.Context
import io.github.aleksandersh.domain.repository.AuthorizationRepository
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.AuthorizationRepositoryImpl
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProviderImpl
import io.github.aleksandersh.simplemvp.PresenterProvider

class ApplicationComponent(val context: Context) {

    val resourceManager: ResourceManager by lazy { ResourceManager(context) }
    val schedulersProvider: SchedulersProvider by lazy { SchedulersProviderImpl() }
    val presenterProvider: PresenterProvider by lazy { PresenterProvider() }
    val authorizationRepository: AuthorizationRepository by lazy { AuthorizationRepositoryImpl() }
}