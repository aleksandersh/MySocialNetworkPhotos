package io.github.aleksandersh.mysocialnetworkphotos.utils

import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class TestSchedulersProvider : SchedulersProvider {

    override val mainThread: Scheduler = Schedulers.currentThread
    override val ioThread: Scheduler = Schedulers.currentThread
    override val computationThread: Scheduler = Schedulers.currentThread
}