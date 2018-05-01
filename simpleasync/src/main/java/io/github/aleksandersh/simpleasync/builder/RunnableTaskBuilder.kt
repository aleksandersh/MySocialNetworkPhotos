package io.github.aleksandersh.simpleasync.builder

import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.operation.*
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class RunnableTaskBuilder<T>(
    asyncTask: AsyncTask,
    private val operation: Operation<T, Unit>
) : BaseTaskBuilder(asyncTask) {

    fun thenRun(runnable: () -> Unit): RunnableTaskBuilder<Unit> {
        return thenRun(Schedulers.currentThread, runnable)
    }

    fun thenRun(
        scheduler: Scheduler,
        runnable: () -> Unit
    ): RunnableTaskBuilder<Unit> {
        val nextOperation = RunnableOperation<Unit>(runnable, scheduler)
        operation.setNext(nextOperation)
        return RunnableTaskBuilder(asyncTask, nextOperation)
    }

    fun <T> thenCall(callable: () -> T): CallableTaskBuilder<Unit, T> {
        return thenCall(Schedulers.currentThread, callable)
    }

    fun <T> thenCall(
        scheduler: Scheduler,
        callable: () -> T
    ): CallableTaskBuilder<Unit, T> {
        val nextOperation = CallableOperation<Unit, T>(callable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
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

    fun anywayRun(runnable: () -> Unit): CallableTaskBuilder<Unit, Unit> {
        return anywayRun(Schedulers.currentThread, runnable)
    }

    fun anywayRun(
        scheduler: Scheduler,
        runnable: () -> Unit
    ): CallableTaskBuilder<Unit, Unit> {
        val nextOperation = AnywayRunnableOperation<Unit>(runnable, scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }

    fun switchScheduler(scheduler: Scheduler): CallableTaskBuilder<Unit, Unit> {
        val nextOperation = SwitchSchedulerOperation<Unit>(scheduler)
        operation.setNext(nextOperation)
        return CallableTaskBuilder(asyncTask, nextOperation)
    }
}