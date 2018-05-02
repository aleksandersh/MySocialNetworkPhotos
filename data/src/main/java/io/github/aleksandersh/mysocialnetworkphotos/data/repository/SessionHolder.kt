package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession

interface SessionHolder {

    fun getCurrentSession(): UserSession?

    fun saveNewSession(session: UserSession)

    fun invalidateSession()
}