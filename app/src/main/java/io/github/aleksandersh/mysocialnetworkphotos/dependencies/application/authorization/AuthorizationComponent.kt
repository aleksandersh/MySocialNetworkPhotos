package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.authorization

import io.github.aleksandersh.domain.usecase.AuthorizationInteractor
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponent
import io.github.aleksandersh.mysocialnetworkphotos.presentation.signin.SignInPresenterFactory

class AuthorizationComponent(private val applicationComponent: ApplicationComponent) {

    val signInPresenterFactory: SignInPresenterFactory by lazy {
        SignInPresenterFactory(
            applicationComponent.resourceManager,
            applicationComponent.schedulersProvider,
            authorizationInteractor
        )
    }

    val authorizationInteractor: AuthorizationInteractor by lazy {
        AuthorizationInteractor(applicationComponent.authorizationRepository)
    }
}