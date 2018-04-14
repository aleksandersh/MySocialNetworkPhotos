package io.github.aleksandersh.simpleasync.builder

import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.operation.*
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class CallableTaskBuilder<P, R>(
    asyncTask: AsyncTask,
    private val operation: Operation<P, R>
) : BaseTaskBuilder(asyncTask) {

    fun thenRun(runnable: () -> Unit): RunnableTaskBuilder<R> {
        return thenRun(Schedulers.currentThread, runnable)
    }

    fun thenRun(
        scheduler: Scheduler,
        runnable: () -> Unit
    ): RunnableTaskBuilder<R> {
        val nextOperation =
            RunnableOperation<R>(runnable, scheduler)
        operation.setNext(nextOperation)
        return RunnableTaskBuilder(asyncTask, nextOperation)
    }

    fun <T> thenCall(callable: () -> T): CallableTaskBuilder<R, T> {
        return thenCall(Schedulers.currentThread, callable)
    }

    fun <T> thenCall(
        scheduler: Scheduler,
        callable: () -> T
    ): CallableTaskBuilder<R, T> {
        val nextOperation = CallableOperation<R, T>(callable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }

    fun <T> thenProcess(callable: (R) -> T): CallableTaskBuilder<R, T> {
        return thenProcess(Schedulers.currentThread, callable)
    }

    fun <T> thenProcess(
        scheduler: Scheduler,
        callable: (R) -> T
    ): CallableTaskBuilder<R, T> {
        val nextOperation = MappingOperation(callable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }

    fun handleError(runnable: (Throwable) -> Unit): CallableTaskBuilder<R, R> {
        return handleError(Schedulers.currentThread, runnable)
    }

    fun handleError(
        scheduler: Scheduler,
        runnable: (Throwable) -> Unit
    ): CallableTaskBuilder<R, R> {
        val nextOperation = HandleErrorOperation<R>(runnable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }
}