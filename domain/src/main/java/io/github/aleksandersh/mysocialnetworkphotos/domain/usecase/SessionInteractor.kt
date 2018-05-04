package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatusSubscriber
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionRepository

class SessionInteractor(private val sessionRepository: SessionRepository) {

    fun checkSessionExists(): Boolean {
        return sessionRepository.checkSession()
    }

    fun subscribeSessionStatus(subscriber: SessionStatusSubscriber) {
        sessionRepository.subscribeSessionStatus(subscriber)
    }

    fun unsubscribeSessionStatus(subscriber: SessionStatusSubscriber) {
        sessionRepository.unsubscribeSessionStatus(subscriber)
    }

    fun logOut() {
        sessionRepository.logOut()
    }
}