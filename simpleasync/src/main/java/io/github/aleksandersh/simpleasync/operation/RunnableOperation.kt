package io.github.aleksandersh.simpleasync.operation

import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

internal class RunnableOperation<P>(
    private val runnable: () -> Unit,
    scheduler: Scheduler
) : AsyncOperation<P, Unit>(scheduler) {

    override fun startOperation(arg: P, taskSession: TaskSession) {
        try {
            onSuccess(runnable(), taskSession)
        } catch (e: Throwable) {
            onError(e, taskSession)
        }
    }
}