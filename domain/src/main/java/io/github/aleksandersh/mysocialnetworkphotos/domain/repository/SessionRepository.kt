package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatusSubscriber

interface SessionRepository {

    fun checkSession(): Boolean

    fun logOut()

    fun subscribeSessionStatus(subscriber: SessionStatusSubscriber)

    fun unsubscribeSessionStatus(subscriber: SessionStatusSubscriber)
}