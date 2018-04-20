package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Root
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.authorization.AuthorizationComponentHolder

class ApplicationComponentHolder(applicationComponent: ApplicationComponent) :
    Root<ApplicationComponent>(applicationComponent) {

    val authorizationComponent: AuthorizationComponentHolder =
        AuthorizationComponentHolder(this)
}