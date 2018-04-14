package io.github.aleksandersh.simpleasync.scheduler

interface Scheduler {

    fun post(runnable: Runnable)
}