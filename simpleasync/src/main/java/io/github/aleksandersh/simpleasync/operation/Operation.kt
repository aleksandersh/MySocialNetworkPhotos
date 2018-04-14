package io.github.aleksandersh.simpleasync.operation

import io.github.aleksandersh.simpleasync.TaskSession

abstract class Operation<ARG, RESULT> {

    private var nextOperation: Operation<RESULT, *>? = null

    internal abstract fun proceedResult(arg: ARG, taskSession: TaskSession)

    internal abstract fun proceedError(error: Throwable, taskSession: TaskSession)

    internal fun setNext(nextOperation: Operation<RESULT, *>) {
        this.nextOperation = nextOperation
    }

    protected fun passResult(arg: RESULT, taskSession: TaskSession) {
        if (taskSession.isNotCancelled()) {
            nextOperation?.proceedResult(arg, taskSession)
        }
    }

    protected fun passError(error: Throwable, taskSession: TaskSession) {
        if (taskSession.isNotCancelled()) {
            nextOperation?.proceedError(error, taskSession)
        }
    }
}