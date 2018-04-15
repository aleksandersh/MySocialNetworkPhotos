package io.github.aleksandersh.mysocialnetworkphotos.utils

import io.github.aleksandersh.simpleasync.scheduler.Scheduler

interface SchedulersProvider {

    val mainThread: Scheduler
    val backgroundThread: Scheduler
}