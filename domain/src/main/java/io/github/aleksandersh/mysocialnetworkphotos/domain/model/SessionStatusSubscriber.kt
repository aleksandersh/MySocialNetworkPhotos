package io.github.aleksandersh.mysocialnetworkphotos.domain.model

interface SessionStatusSubscriber {

    fun handleSessionStatus(status: SessionStatus)
}