package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import android.support.annotation.StringRes
import io.github.aleksandersh.mysocialnetworkphotos.R
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatus
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatusSubscriber
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.SessionInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simplemvp.Presenter

class ContentPresenter(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val sessionInteractor: SessionInteractor
) : Presenter, SessionStatusSubscriber {

    val viewState: ContentViewState = ContentViewState()

    private var initialized = false
    private var sessionChecked = false
    private var sessionCheckTask: TaskSession? = null

    override fun onDestroy() {
        sessionCheckTask?.cancel()
        Tree.applicationComponent.contentComponent.release(ContentView.TAG)
        sessionInteractor.unsubscribeSessionStatus(this)
    }

    override fun handleSessionStatus(status: SessionStatus) {
        if (status == SessionStatus.FINISHED) {
            viewState.screen.set(Screen.AUTHORIZATION)
        }
    }

    fun onActivityCreated() {
        if (!initialized) {
            initialized = true
            sessionInteractor.subscribeSessionStatus(this)
        }

        if (!sessionChecked) checkSession()
    }

    fun onClickRetry() {
        checkSession()
    }

    private fun checkSession() {
        showLoading()
        sessionCheckTask = AsyncTask.firstCall(schedulersProvider.backgroundThread) {
            sessionInteractor.checkSessionExists()
        }
            .switchScheduler(schedulersProvider.mainThread)
            .thenProcess(::handleSessionResult)
            .handleError(::handleSessionError)
            .start()
    }

    private fun handleSessionResult(exists: Boolean) {
        sessionChecked = true
        if (exists) {
            viewState.screen.set(Screen.FRIENDS)
            viewState.contentScreen.set(true)
        } else {
            viewState.screen.set(Screen.AUTHORIZATION)
        }
    }

    private fun handleSessionError(error: Throwable) {
        showError(R.string.all_error_default_title, R.string.all_error_default_subtitle)
    }

    private fun showLoading() {
        showZeroScreen(
            titleRes = R.string.all_loading_default_title,
            subtitleRes = R.string.all_loading_default_subtitle,
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
}