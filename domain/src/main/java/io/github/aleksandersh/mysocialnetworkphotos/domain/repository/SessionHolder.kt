package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession

interface SessionHolder {

    fun getCurrentSession(): UserSession?

    fun saveNewSession(session: UserSession)
}