package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.FriendsInteractor
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simplemvp.PresenterFactory

class FriendsPresenterFactory(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val friendsInteractor: FriendsInteractor,
    private val photoInteractor: PhotoInteractor
) : PresenterFactory<FriendsPresenter> {

    override fun create(): FriendsPresenter {
        return FriendsPresenter(
            resourceManager,
            schedulersProvider,
            friendsInteractor,
            photoInteractor
        )
    }
}