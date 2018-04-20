package io.github.aleksandersh.domain.usecase

import io.github.aleksandersh.domain.repository.AuthorizationRepository

class AuthorizationInteractor(
    private val authorizationRepository: AuthorizationRepository
) {

    fun logIn(login: String, password: String) {
        authorizationRepository.logIn(login, password)
    }

    fun logOut() {
        authorizationRepository.logOut()
    }
}