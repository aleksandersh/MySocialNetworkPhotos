package io.github.aleksandersh.simpleasync.operation

import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

abstract class AsyncOperation<ARG, RESULT>(
    private val scheduler: Scheduler
) : Operation<ARG, RESULT>() {

    protected abstract fun startOperation(arg: ARG, taskSession: TaskSession)

    override fun proceedResult(arg: ARG, taskSession: TaskSession) {
        if (taskSession.isNotCancelled()) {
            scheduler.post(Runnable { startOperation(arg, taskSession) })
        }
    }

    override fun proceedError(error: Throwable, taskSession: TaskSession) {
        passError(error, taskSession)
    }

    protected open fun onSuccess(arg: RESULT, taskSession: TaskSession) {
        passResult(arg, taskSession)
    }

    protected open fun onError(error: Throwable, taskSession: TaskSession) {
        passError(error, taskSession)
    }
}