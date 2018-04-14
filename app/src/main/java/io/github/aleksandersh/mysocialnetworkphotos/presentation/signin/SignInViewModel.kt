package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.domain.AuthorizationInteractor
import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simplemvp.Presenter

class SignInViewModel(
    private val authorizationInteractor: AuthorizationInteractor
) : Presenter {

    private var authorizationTask: TaskSession? = null

    override fun onDestroy() {
        authorizationTask?.cancel()
    }

    fun onClickLogIn(loginInput: CharSequence, passwordInput: CharSequence) {
        val login = loginInput.toString()
        val password = passwordInput.toString()
        if (checkCredentials(login, password)) {
            authorizationTask = AsyncTask
                .firstRun(Schedulers.backgroundScheduler) {
                    authorizationInteractor.login(login, password)
                }
                .thenRun(Schedulers.mainScheduler, ::onAuthorizationSuccess)
                .handleError(Schedulers.mainScheduler, ::onAuthorizationError)
                .start()
        }
    }

    private fun onAuthorizationSuccess() {

    }

    private fun onAuthorizationError(error: Throwable) {

    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return true
    }
}