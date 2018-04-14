package io.github.aleksandersh.simpleasync.operation

import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class CallableOperation<ARG, RESULT>(
    private val callable: () -> RESULT,
    scheduler: Scheduler
) : AsyncOperation<ARG, RESULT>(scheduler) {

    override fun startOperation(arg: ARG, taskSession: TaskSession) {
        try {
            onSuccess(callable(), taskSession)
        } catch (e: Throwable) {
            onError(e, taskSession)
        }
    }
}