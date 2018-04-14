package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.simplemvp.ViewModelFactory

class SignInViewModelFactory : ViewModelFactory<SignInViewModel> {

    override fun create(): SignInViewModel {
        return SignInViewModel()
    }
}