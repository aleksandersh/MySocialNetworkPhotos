package io.github.aleksandersh.mysocialnetworkphotos.presentation.photo

import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInfoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.PhotoInteractor
import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simplemvp.PresenterFactory

class PhotoPresenterFactory(
    private val resourceManager: ResourceManager,
    private val schedulersProvider: SchedulersProvider,
    private val photoInteractor: PhotoInteractor,
    private val photoInfoInteractor: PhotoInfoInteractor
) : PresenterFactory<PhotoPresenter> {

    override fun create(): PhotoPresenter {
        return PhotoPresenter(
            resourceManager,
            schedulersProvider,
            photoInteractor,
            photoInfoInteractor
        )
    }
}