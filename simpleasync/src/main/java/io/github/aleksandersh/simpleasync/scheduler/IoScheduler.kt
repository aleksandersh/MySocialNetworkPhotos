package io.github.aleksandersh.simpleasync.scheduler

import java.util.concurrent.Executors

/**
 * Simple [Scheduler] implementation for I/O intensive operations.
 */
class IoScheduler : Scheduler {

    private val executor = Executors.newCachedThreadPool()

    override fun post(runnable: Runnable) {
        executor.submit(runnable)
    }
}