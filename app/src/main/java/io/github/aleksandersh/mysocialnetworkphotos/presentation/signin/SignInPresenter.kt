package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.domain.AuthorizationInteractor
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simplemvp.Presenter

class SignInPresenter(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val authorizationInteractor: AuthorizationInteractor
) : Presenter {

    val viewState = SignInViewState()

    private var authorizationTask: TaskSession? = null

    override fun onDestroy() {
        authorizationTask?.cancel()
    }

    fun onClickLogIn(loginInput: CharSequence, passwordInput: CharSequence) {
        val login = loginInput.toString()
        val password = passwordInput.toString()
        if (checkCredentials(login, password)) {
            showProgress()
            authorizationTask = AsyncTask
                .firstRun(schedulersProvider.backgroundThread) {
                    authorizationInteractor.logIn(login, password)
                }
                .anywayRun(schedulersProvider.mainThread, ::hideProgress)
                .thenRun(::onAuthorizationSuccess)
                .handleError(::onAuthorizationError)
                .start()
        }
    }

    private fun onAuthorizationSuccess() {
        viewState.complete.set(true)
    }

    private fun onAuthorizationError(error: Throwable) {
        viewState.error.set("Error")
    }

    private fun showProgress() {
        viewState.progress.set(true)
    }

    private fun hideProgress() {
        viewState.progress.set(false)
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login.isNotBlank() && password.isNotBlank()
    }
}