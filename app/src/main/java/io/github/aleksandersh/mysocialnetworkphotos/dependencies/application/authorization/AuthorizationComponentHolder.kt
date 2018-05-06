package io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.authorization

import io.github.aleksandersh.mysocialnetworkphotos.dependencies.Branch
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponent
import io.github.aleksandersh.mysocialnetworkphotos.dependencies.application.ApplicationComponentHolder

class AuthorizationComponentHolder(parent: ApplicationComponentHolder) :
    Branch<ApplicationComponent, AuthorizationComponent>(parent) {

    override fun create(parent: ApplicationComponent): AuthorizationComponent {
        return AuthorizationComponent(parent)
    }
}