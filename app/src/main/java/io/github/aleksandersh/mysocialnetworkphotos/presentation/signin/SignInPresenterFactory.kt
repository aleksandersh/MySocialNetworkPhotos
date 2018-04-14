package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.simplemvp.PresenterFactory

class SignInPresenterFactory : PresenterFactory<SignInViewModel> {

    override fun create(): SignInViewModel {
        return SignInViewModel()
    }
}