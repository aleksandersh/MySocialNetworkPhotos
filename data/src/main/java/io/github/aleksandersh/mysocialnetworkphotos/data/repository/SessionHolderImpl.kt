package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.storage.SessionStorage
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatus
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.SessionStatusSubscriber
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.UserSession
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference

class SessionHolderImpl(private val sessionStorage: SessionStorage) : SessionHolder {

    private var currentSession: AtomicReference<UserSession?> = AtomicReference(null)

    private val sessionStatus: AtomicReference<SessionStatus?> = AtomicReference(null)
    private val subscribers: MutableList<SessionStatusSubscriber> = CopyOnWriteArrayList()

    override fun getCurrentSession(): UserSession? {
        return currentSession.get() ?: initializeSession()
    }

    /**
     * Main thread only.
     */
    override fun subscribeSessionStatus(subscriber: SessionStatusSubscriber) {
        val current = sessionStatus.get()
        if (current != null) {
            subscriber.handleSessionStatus(current)
        } else {
            initializeSession()
        }

        if (subscribers.find { it === subscriber } == null) {
            subscribers.add(subscriber)
        }
    }

    /**
     * Main thread only.
     */
    override fun unsubscribeSessionStatus(subscriber: SessionStatusSubscriber) {
        val idx = subscribers.indexOfFirst { it === subscriber }
        subscribers.removeAt(idx)
    }

    override fun saveNewSession(session: UserSession) {
        currentSession.set(session)
        changeStatus(SessionStatus.ACTIVE)
        sessionStorage.saveSession(session)
    }

    override fun invalidateSession() {
        currentSession.set(null)
        changeStatus(SessionStatus.FINISHED)
        sessionStorage.clearSession()
    }

    private fun initializeSession(): UserSession? {
        val stored = sessionStorage.getSession()
        if (stored != null) {
            if (currentSession.compareAndSet(null, stored)) {
                changeStatus(SessionStatus.ACTIVE)
                return stored
            } else {
                return currentSession.get()
            }
        } else {
            changeStatus(SessionStatus.FINISHED)
        }
        return null
    }

    private fun changeStatus(status: SessionStatus) {
        sessionStatus.set(status)
        subscribers.forEach { it.handleSessionStatus(status) }
    }
}