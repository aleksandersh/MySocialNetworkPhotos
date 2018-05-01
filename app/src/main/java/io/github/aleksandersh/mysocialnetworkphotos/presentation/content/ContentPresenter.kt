package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.SessionInteractor
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simplemvp.Presenter

class ContentPresenter(
    private val schedulersProvider: SchedulersProvider,
    private val sessionInteractor: SessionInteractor
) : Presenter {

    val viewState: ContentViewState = ContentViewState()

    private var sessionChecked = false
    private var sessionCheckTask: TaskSession? = null

    override fun onDestroy() {
        sessionCheckTask?.cancel()
        Tree.applicationComponent.contentComponent.release(ContentView.TAG)
    }

    fun onActivityCreated() {
        if (!sessionChecked) checkSession()
    }

    private fun checkSession() {
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
        } else {
            viewState.screen.set(Screen.AUTHORIZATION)
        }
    }

    private fun handleSessionError(error: Throwable) {
        // TODO
    }
}