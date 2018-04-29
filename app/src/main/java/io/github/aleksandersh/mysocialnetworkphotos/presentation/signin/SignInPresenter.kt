package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import android.support.annotation.StringRes
import android.webkit.WebResourceError
import io.github.aleksandersh.mysocialnetworkphotos.R
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

    private lateinit var webClientState: WebClientState

    init {
        loadStartPage()
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

    fun onPageStarted() {
        webClientState.onPageStarted()
    }

    fun onPageFinished() {
        webClientState.onPageFinished()
    }

    fun onReceivedError(error: WebResourceError) {
        webClientState.onError(error)
    }

    fun onClickRetry() {
        loadStartPage()
    }

    private fun loadStartPage() {
        switchState(LoadingState())
        viewState.loadUrl.set(startPageQuery)
    }

    private fun showContent() {
        viewState.contentScreen.set(true)
    }

    private fun showLoading() {
        showZeroScreen(
            titleRes = R.string.fragment_sign_in_loading_title,
            subtitleRes = R.string.fragment_sign_in_loading_subtitle,
            progress = true
        )
    }

    private fun showError(@StringRes titleRes: Int, @StringRes subtitleRes: Int) {
        showZeroScreen(
            titleRes = titleRes,
            subtitleRes = subtitleRes,
            retry = true
        )
    }

    private fun showZeroScreen(
        @StringRes titleRes: Int,
        @StringRes subtitleRes: Int,
        retry: Boolean = false,
        progress: Boolean = false
    ) {
        val title = resourceManager.getString(titleRes)
        val subtitle = resourceManager.getString(subtitleRes)
        viewState.zeroScreenData.set(
            ZeroScreenData(
                title = title,
                subtitle = subtitle,
                retry = retry,
                progress = progress
            )
        )
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

    private fun switchState(newState: WebClientState) {
        webClientState = newState
        newState.show()
    }

    abstract class WebClientState {

        abstract fun show()

        open fun onPageStarted() {}

        open fun onPageFinished() {}

        open fun onError(error: WebResourceError) {}
    }

    inner class ContentState : WebClientState() {

        override fun show() {
            showContent()
        }

        override fun onError(error: WebResourceError) {
            switchState(ErrorState(error))
        }
    }

    inner class LoadingState : WebClientState() {

        override fun show() {
            showLoading()
        }

        override fun onPageFinished() {
            switchState(ContentState())
        }

        override fun onError(error: WebResourceError) {
            switchState(ErrorState(error))
        }
    }

    inner class ErrorState(error: WebResourceError) : WebClientState() {

        override fun show() {
            showError(
                R.string.fragment_sign_in_error_title,
                R.string.fragment_sign_in_error_subtitle
            )
        }

        override fun onPageStarted() {
            switchState(LoadingState())
        }
    }
}