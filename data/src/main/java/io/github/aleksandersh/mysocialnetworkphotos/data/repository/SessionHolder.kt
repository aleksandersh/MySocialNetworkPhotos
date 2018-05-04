package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatusSubscriber
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession

interface SessionHolder {

    fun getCurrentSession(): UserSession?

    fun subscribeSessionStatus(subscriber: SessionStatusSubscriber)

    fun unsubscribeSessionStatus(subscriber: SessionStatusSubscriber)

    fun saveNewSession(session: UserSession)

    fun invalidateSession()
}