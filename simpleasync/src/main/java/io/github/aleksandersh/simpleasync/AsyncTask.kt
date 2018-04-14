package io.github.aleksandersh.simpleasync

import io.github.aleksandersh.simpleasync.builder.CallableTaskBuilder
import io.github.aleksandersh.simpleasync.builder.RunnableTaskBuilder
import io.github.aleksandersh.simpleasync.operation.CallableOperation
import io.github.aleksandersh.simpleasync.operation.Operation
import io.github.aleksandersh.simpleasync.operation.RunnableOperation
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class AsyncTask private constructor(private val operation: Operation<Unit, *>) {

    companion object {

        fun firstRun(scheduler: Scheduler, runnable: () -> Unit): RunnableTaskBuilder<Unit> {
            val processor = RunnableOperation<Unit>(
                runnable,
                scheduler
            )
            val asyncTask = AsyncTask(processor)
            return RunnableTaskBuilder(asyncTask, processor)
        }

        fun <T> firstCall(scheduler: Scheduler, callable: () -> T): CallableTaskBuilder<Unit, T> {
            val processor = CallableOperation<Unit, T>(
                callable,
                scheduler
            )
            val asyncTask = AsyncTask(processor)
            return CallableTaskBuilder(asyncTask, processor)
        }
    }

    fun start(): TaskSession {
        val taskSession = TaskSession()
        operation.proceedResult(Unit, taskSession)
        return taskSession
    }
}