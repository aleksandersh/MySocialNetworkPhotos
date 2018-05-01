package io.github.aleksandersh.simpleasync.operation

import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class SwitchSchedulerOperation<P>(
    private val scheduler: Scheduler
) : Operation<P, P>() {

    override fun proceedResult(arg: P, taskSession: TaskSession) {
        if (taskSession.isNotCancelled()) {
            scheduler.post(Runnable {
                passResult(arg, taskSession)
            })
        }
    }

    override fun proceedError(error: Throwable, taskSession: TaskSession) {
        if (taskSession.isNotCancelled()) {
            scheduler.post(Runnable {
                passError(error, taskSession)
            })
        }
    }
}