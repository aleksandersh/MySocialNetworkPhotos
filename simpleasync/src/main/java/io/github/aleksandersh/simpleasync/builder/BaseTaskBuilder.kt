package io.github.aleksandersh.simpleasync.builder

import io.github.aleksandersh.simpleasync.AsyncTask
import io.github.aleksandersh.simpleasync.TaskSession

open class BaseTaskBuilder(protected val asyncTask: AsyncTask) {

    fun build(): AsyncTask = asyncTask

    fun start(): TaskSession = asyncTask.start()
}