package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Root
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.authorization.AuthorizationComponentHolder
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.content.ContentComponentHolder

class ApplicationComponentHolder(applicationComponent: ApplicationComponent) :
    Root<ApplicationComponent>(applicationComponent) {

    val authorizationComponent: AuthorizationComponentHolder by lazy {
        AuthorizationComponentHolder(this)
    }
    val contentComponent: ContentComponentHolder by lazy {
        ContentComponentHolder(this)
    }
}