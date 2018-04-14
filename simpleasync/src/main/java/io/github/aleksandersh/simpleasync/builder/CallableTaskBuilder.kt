package io.github.aleksandersh.simpleasync.builder

import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.operation.*
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class CallableTaskBuilder<P, R>(
    asyncTask: AsyncTask,
    private val operation: Operation<P, R>
) : BaseTaskBuilder(asyncTask) {

    fun thenRun(
        scheduler: Scheduler = Schedulers.currentThread,
        runnable: () -> Unit
    ): RunnableTaskBuilder<R> {
        val nextOperation =
            RunnableOperation<R>(runnable, scheduler)
        operation.setNext(nextOperation)
        return RunnableTaskBuilder(asyncTask, nextOperation)
    }

    fun <T> thenCall(
        scheduler: Scheduler = Schedulers.currentThread,
        callable: () -> T
    ): CallableTaskBuilder<R, T> {
        val nextOperation = CallableOperation<R, T>(callable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }

    fun <T> thenProcess(
        scheduler: Scheduler = Schedulers.currentThread,
        callable: (R) -> T
    ): CallableTaskBuilder<R, T> {
        val nextOperation = MappingOperation(callable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }

    fun handleError(
        scheduler: Scheduler = Schedulers.currentThread,
        runnable: (Throwable) -> Unit
    ): CallableTaskBuilder<R, R> {
        val nextOperation = HandleErrorOperation<R>(runnable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }
}