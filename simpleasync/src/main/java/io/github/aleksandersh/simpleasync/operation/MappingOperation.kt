package io.github.aleksandersh.simpleasync.operation

import io.github.aleksandersh.simpleasync.TaskSession
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class MappingOperation<ARG, RESULT>(
    private val callable: (ARG) -> RESULT,
    scheduler: Scheduler
) : AsyncOperation<ARG, RESULT>(scheduler) {

    override fun startOperation(arg: ARG, taskSession: TaskSession) {
        try {
            onSuccess(callable(arg), taskSession)
        } catch (e: Throwable) {
            onError(e, taskSession)
        }
    }
}
