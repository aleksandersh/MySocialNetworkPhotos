package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.SessionInteractor
import io.github.aleksandersh.mysocialnetworkphotos.utils.SchedulersProvider
import io.github.aleksandersh.simplemvp.PresenterFactory

class ContentPresenterFactory(
    private val schedulersProvider: SchedulersProvider,
    private val sessionInteractor: SessionInteractor
) : PresenterFactory<ContentPresenter> {

    override fun create(): ContentPresenter {
        return ContentPresenter(schedulersProvider, sessionInteractor)
    }
}