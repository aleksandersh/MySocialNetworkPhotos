package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import android.webkit.WebResourceError
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.AuthorizationAction
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.AuthorizationInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
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

    private val startPageQuery: String = authorizationInteractor.getAuthorizationQuery()

    private var authorizationTask: TaskSession? = null

    init {
        viewState.loadUrl.set(startPageQuery)
    }

    override fun onDestroy() {
        authorizationTask?.cancel()
        Tree.applicationComponent.authorizationComponent.release(SignInView.TAG)
    }

    fun onUrlLoading(url: String): Boolean {
        val handle = authorizationInteractor.checkHandling(url)
        if (handle) {
            authorizationTask = AsyncTask.firstCall(schedulersProvider.backgroundThread) {
                authorizationInteractor.processUrl(url)
            }
                .thenProcess(schedulersProvider.mainThread, ::onUrlProcessed)
                .start()
        }

        return handle
    }

    fun onClickRetry() {
        viewState.loadUrl.set(startPageQuery)
    }

    fun onReceivedError(error: WebResourceError) {
        viewState.zeroScreenData.set(
            ZeroScreenData(
                title = "Oops!",
                subtitle = "Error",
                retry = true
            )
        ) // TODO
        viewState.contentScreen.set(false)
    }

    private fun onUrlProcessed(action: AuthorizationAction) {
        viewState.contentScreen.set(true)
        when (action) {
            AuthorizationAction.AUTHORIZED -> {
                viewState.complete.set(true)
            }
            AuthorizationAction.CANCEL -> {
                viewState.cancel.set(true)
            }
        }
    }
}