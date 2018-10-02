package io.github.aleksandersh.simpleasync.scheduler

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Simple [Scheduler] implementation for CPU intensive operations.
 */
class ComputationScheduler : Scheduler {

    private val executor: ExecutorService

    init {
        val availableProcessors = Runtime.getRuntime().availableProcessors()
        executor = Executors.newFixedThreadPool(availableProcessors)
    }

    override fun post(runnable: Runnable) {
        executor.submit(runnable)
    }
}