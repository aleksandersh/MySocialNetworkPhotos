package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationHolder

class SessionInteractor(private val authorizationHolder: AuthorizationHolder) {

    fun checkSessionExists(): Boolean {
        return authorizationHolder.checkSessionExists()
    }
}