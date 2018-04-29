package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponent
import io.github.aleksandersh.mysocialnetworkphotos.domain.usecase.SessionInteractor
import io.github.aleksandersh.mysocialnetworkphotos.presentation.content.ContentPresenterFactory

class ContentComponent(val applicationComponent: ApplicationComponent) {

    val presenterFactory: ContentPresenterFactory by lazy {
        ContentPresenterFactory(
            applicationComponent.schedulersProvider,
            sessionInteractor
        )
    }

    private val sessionInteractor: SessionInteractor by lazy {
        SessionInteractor(applicationComponent.authorizationHolder)
    }
}