package io.github.aleksandersh.simpleasync

import java.util.concurrent.atomic.AtomicBoolean

class TaskSession {

    private val cancelled = AtomicBoolean(false)

    fun isCancelled() = cancelled.get()

    fun isNotCancelled() = !cancelled.get()

    fun cancel() {
        cancelled.set(true)
    }
}