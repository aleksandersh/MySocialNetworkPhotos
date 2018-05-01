package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionRepository

class SessionInteractor(private val sessionRepository: SessionRepository) {

    fun checkSessionExists(): Boolean {
        return sessionRepository.checkSession()
    }
}