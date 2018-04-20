package io.github.aleksandersh.domain.repository

interface AuthorizationRepository {

    fun logIn(login: String, password: String)

    fun logOut()

    fun confirmCurrentSession(): Boolean
}