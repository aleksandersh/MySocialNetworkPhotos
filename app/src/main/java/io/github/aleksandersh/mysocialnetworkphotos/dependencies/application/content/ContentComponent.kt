package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content

import io.github.aleksandersh.mysocialnetworkphotos.data.datasource.SessionHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.PhotoRepositoryImpl
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.SessionRepositoryImpl
import io.github.aleksandersh.mysocialnetworkphotos.data.storage.PhotoCache
import io.github.aleksandersh.mysocialnetworkphotos.data.storage.PhotoLightweightCache
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponent
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.PhotoRepository
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionRepository
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.SessionInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.content.ContentPresenterFactory

class ContentComponent(val applicationComponent: ApplicationComponent) {

    val presenterFactory: ContentPresenterFactory by lazy {
        ContentPresenterFactory(
            applicationComponent.resourceManager,
            applicationComponent.schedulersProvider,
            sessionInteractor
        )
    }

    val photoInteractor: PhotoInteractor by lazy {
        PhotoInteractor(photoRepository)
    }

    val sessionInteractor: SessionInteractor by lazy {
        SessionInteractor(sessionRepository)
    }

    private val sessionRepository: SessionRepository by lazy {
        SessionRepositoryImpl(
            sessionHttpDatasource,
            applicationComponent.sessionHolder,
            photoRepository
        )
    }

    private val sessionHttpDatasource: SessionHttpDatasource by lazy {
        SessionHttpDatasource(
            applicationComponent.httpClient,
            applicationComponent.responseErrorHandler,
            applicationComponent.sessionHolder
        )
    }

    private val photoRepository: PhotoRepository by lazy {
        PhotoRepositoryImpl(applicationComponent.httpClient, photoLightweightCache)
    }

    private val photoLightweightCache: PhotoCache by lazy {
        PhotoLightweightCache()
    }
}