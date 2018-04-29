package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession

interface AuthorizationHolder {

    fun getCurrentSession(): UserSession?

    fun saveNewSession(session: UserSession)

    fun checkSessionExists(): Boolean
}