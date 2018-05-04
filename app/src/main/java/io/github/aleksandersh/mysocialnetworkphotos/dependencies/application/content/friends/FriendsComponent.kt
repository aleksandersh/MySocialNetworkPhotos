package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.friends

import io.github.aleksandersh.mysocialnetworkphotos.data.datasource.FriendsHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.FriendsRepositoryImpl
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.ContentComponent
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.FriendsRepository
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.FriendsInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.FriendsPresenterFactory

class FriendsComponent(val contentComponent: ContentComponent) {

    val friendsPresenterFactory: FriendsPresenterFactory by lazy {
        FriendsPresenterFactory(
            contentComponent.applicationComponent.resourceManager,
            contentComponent.applicationComponent.schedulersProvider,
            friendsInteractor,
            contentComponent.photoInteractor,
            contentComponent.sessionInteractor
        )
    }

    private val friendsInteractor: FriendsInteractor by lazy {
        FriendsInteractor(friendsRepository)
    }

    private val friendsRepository: FriendsRepository by lazy {
        FriendsRepositoryImpl(friendsHttpDatasource)
    }

    private val friendsHttpDatasource: FriendsHttpDatasource by lazy {
        FriendsHttpDatasource(
            contentComponent.applicationComponent.httpClient,
            contentComponent.applicationComponent.responseErrorHandler,
            contentComponent.applicationComponent.sessionHolder
        )
    }
}