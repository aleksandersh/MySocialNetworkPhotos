package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.datasource.SessionHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatusSubscriber
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.PhotoRepository
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val sessionHttpDatasource: SessionHttpDatasource,
    private val sessionHolder: SessionHolder,
    private val photoRepository: PhotoRepository
) : SessionRepository {

    override fun checkSession(): Boolean {
        return sessionHttpDatasource.checkSession()
    }

    override fun logOut() {
        photoRepository.clearCache()
        sessionHolder.invalidateSession()
    }

    override fun subscribeSessionStatus(subscriber: SessionStatusSubscriber) {
        sessionHolder.subscribeSessionStatus(subscriber)
    }

    override fun unsubscribeSessionStatus(subscriber: SessionStatusSubscriber) {
        sessionHolder.unsubscribeSessionStatus(subscriber)
    }
}