package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.domain.repository.AuthorizationRepository

class AuthorizationRepositoryImpl : AuthorizationRepository {

    private var currentSession: Boolean = false

    override fun logIn(login: String, password: String) {
        Thread.sleep(3000)
        currentSession = true
    }

    override fun logOut() {
        Thread.sleep(3000)
        currentSession = false
    }

    override fun confirmCurrentSession(): Boolean {
        Thread.sleep(3000)
        return currentSession
    }
}