package io.github.aleksandersh.simpleasync.builder

import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.operation.CallableOperation
import io.github.aleksandersh.simpleasync.operation.HandleErrorOperation
import io.github.aleksandersh.simpleasync.operation.Operation
import io.github.aleksandersh.simpleasync.operation.RunnableOperation
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class RunnableTaskBuilder<T>(
    asyncTask: AsyncTask,
    private val operation: Operation<T, Unit>
) : BaseTaskBuilder(asyncTask) {

    fun <T> thenCall(
        scheduler: Scheduler = Schedulers.currentThread,
        callable: () -> T
    ): CallableTaskBuilder<Unit, T> {
        val nextOperation = CallableOperation<Unit, T>(callable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }

    fun thenRun(
        scheduler: Scheduler = Schedulers.currentThread,
        runnable: () -> Unit
    ): RunnableTaskBuilder<Unit> {
        val nextOperation = RunnableOperation<Unit>(runnable, scheduler)
        operation.setNext(nextOperation)
        return RunnableTaskBuilder(asyncTask, nextOperation)
    }

    fun handleError(runnable: (Throwable) -> Unit): RunnableTaskBuilder<Unit> {
        return handleError(Schedulers.currentThread, runnable)
    }

    fun handleError(
        scheduler: Scheduler,
        runnable: (Throwable) -> Unit
    ): RunnableTaskBuilder<Unit> {
        val nextOperation = HandleErrorOperation<Unit>(runnable, scheduler)
        operation.setNext(nextOperation)
        return RunnableTaskBuilder(asyncTask, nextOperation)
    }
}