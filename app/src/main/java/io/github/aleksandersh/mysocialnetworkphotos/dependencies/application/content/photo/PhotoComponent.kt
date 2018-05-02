package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.photo

import io.github.aleksandersh.mysocialnetworkphotos.data.datasource.PhotoInfoHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.data.repository.PhotoInfoRepositoryImpl
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.ContentComponent
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.PhotoInfoRepository
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInfoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.photo.PhotoPresenterFactory

class PhotoComponent(val contentComponent: ContentComponent) {

    val photoPresenterFactory: PhotoPresenterFactory by lazy {
        PhotoPresenterFactory(
            contentComponent.applicationComponent.resourceManager,
            contentComponent.applicationComponent.schedulersProvider,
            contentComponent.photoInteractor,
            photoInfoInteractor
        )
    }

    private val photoInfoInteractor: PhotoInfoInteractor by lazy {
        PhotoInfoInteractor(photoInfoRepository)
    }

    private val photoInfoRepository: PhotoInfoRepository by lazy {
        PhotoInfoRepositoryImpl(photoInfoHttpDatasource)
    }

    private val photoInfoHttpDatasource: PhotoInfoHttpDatasource by lazy {
        PhotoInfoHttpDatasource(
            contentComponent.applicationComponent.httpClient,
            contentComponent.applicationComponent.responseErrorHandler,
            contentComponent.applicationComponent.sessionHolder
        )
    }
}