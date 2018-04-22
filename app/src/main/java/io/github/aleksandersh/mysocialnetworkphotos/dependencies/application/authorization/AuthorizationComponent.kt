package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.authorization

import io.github.aleksandersh.mysocialnetworkphotos.data.repository.AuthorizationRepositoryImpl
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponent
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationRepository
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.AuthorizationInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.signin.SignInPresenterFactory

class AuthorizationComponent(val applicationComponent: ApplicationComponent) {

    val signInPresenterFactory: SignInPresenterFactory by lazy {
        SignInPresenterFactory(
            applicationComponent.resourceManager,
            applicationComponent.schedulersProvider,
            authorizationInteractor
        )
    }

    private val authorizationInteractor: AuthorizationInteractor by lazy {
        AuthorizationInteractor(authorizationRepository)
    }

    private val authorizationRepository: AuthorizationRepository by lazy {
        AuthorizationRepositoryImpl(applicationComponent.authorizationHolder)
    }
}