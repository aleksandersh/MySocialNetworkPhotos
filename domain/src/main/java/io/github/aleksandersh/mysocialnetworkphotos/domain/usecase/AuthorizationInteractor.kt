package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.AuthorizationAction
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.AuthorizationRepository

class AuthorizationInteractor(private val authorizationRepository: AuthorizationRepository) {

    fun getAuthorizationQuery(): String {
        return authorizationRepository.getAuthorizationProperties().authorizationQuery
    }

    fun checkHandling(url: String): Boolean {
        return authorizationRepository.checkUrlHandling(url)
    }

    fun processUrl(url: String): AuthorizationAction {
        if (authorizationRepository.checkUrlForLoginAndHandleSession(url)) {
            return AuthorizationAction.AUTHORIZED
        }
        if (authorizationRepository.checkUrlForCancel(url)) {
            return AuthorizationAction.CANCEL
        }

        return AuthorizationAction.UNSPECIFIED
    }
}