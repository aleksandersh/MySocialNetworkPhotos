package io.github.aleksandersh.mysocialnetworkphotos.utils

import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class TestSchedulersProvider : SchedulersProvider {

    override val mainThread: Scheduler
        get() = Schedulers.currentThread

    override val backgroundThread: Scheduler
        get() = Schedulers.currentThread
}