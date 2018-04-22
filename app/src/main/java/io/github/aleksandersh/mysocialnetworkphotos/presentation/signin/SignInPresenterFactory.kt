package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.AuthorizationInteractor
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simplemvp.PresenterFactory

class SignInPresenterFactory(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val authorizationInteractor: AuthorizationInteractor
) : PresenterFactory<SignInPresenter> {

    override fun create(): SignInPresenter {
        return SignInPresenter(resourceManager, schedulersProvider, authorizationInteractor)
    }
}