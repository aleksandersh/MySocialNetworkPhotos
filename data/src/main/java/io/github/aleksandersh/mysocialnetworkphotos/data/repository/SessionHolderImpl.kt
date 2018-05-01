package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.storage.SessionStorage
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionHolder
import java.util.concurrent.atomic.AtomicReference

class SessionHolderImpl(private val sessionStorage: SessionStorage) : SessionHolder {

    private var currentSession: AtomicReference<UserSession?> = AtomicReference(null)

    override fun getCurrentSession(): UserSession? {
        val current = currentSession.get()
        if (current != null) {
            return current
        } else {
            val stored = sessionStorage.getSession()
            if (stored != null) {
                currentSession.compareAndSet(null, stored)
                return stored
            }
        }

        return null
    }

    override fun saveNewSession(session: UserSession) {
        currentSession.set(session)
        sessionStorage.saveSession(session)
    }
}