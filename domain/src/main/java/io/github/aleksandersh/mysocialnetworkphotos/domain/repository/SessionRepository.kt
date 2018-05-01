package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

interface SessionRepository {

    fun checkSession(): Boolean
}