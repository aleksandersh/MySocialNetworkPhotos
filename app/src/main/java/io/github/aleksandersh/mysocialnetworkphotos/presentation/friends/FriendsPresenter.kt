package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Tree
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.FriendsInteractor
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simplemvp.Presenter

class FriendsPresenter(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val friendsInteractor: FriendsInteractor
) : Presenter {

    val viewState: FriendsViewState = FriendsViewState()

    override fun onDestroy() {
        Tree.applicationComponent
            .contentComponent
            .friendsComponent
            .release(FriendsView.TAG)
    }

    fun loadFriends() {
        AsyncTask.firstCall(schedulersProvider.backgroundThread) {
            friendsInteractor.getFriends()
        }
            .start()
    }
}