package io.github.aleksandersh.simpleasync.operation

import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class HandleErrorOperation<R>(
    private val runnable: (Throwable) -> Unit,
    private val scheduler: Scheduler
) : Operation<R, R>() {

    override fun proceedResult(arg: R, taskSession: TaskSession) {
        passResult(arg, taskSession)
    }

    override fun proceedError(error: Throwable, taskSession: TaskSession) {
        if (taskSession.isNotCancelled()) {
            scheduler.post(Runnable { runnable(error) })
        }
    }
}