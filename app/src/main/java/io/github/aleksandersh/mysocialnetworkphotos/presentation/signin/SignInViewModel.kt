package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.domain.AuthorizationInteractor
import io.github.aleksandersh.simplemvp.Presenter

class SignInViewModel(
    private val authorizationInteractor: AuthorizationInteractor
) : Presenter {

    override fun onDestroy() {
    }

    fun onClickLogIn(loginInput: CharSequence, passwordInput: CharSequence) {
        val login = loginInput.toString()
        val password = passwordInput.toString()
        if (checkCredentials(login, password)) {
            authorizationInteractor.login(login, password)
        }
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return true
    }
}