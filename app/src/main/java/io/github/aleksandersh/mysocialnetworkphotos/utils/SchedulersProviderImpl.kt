package io.github.aleksandersh.mysocialnetworkphotos.utils

import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class SchedulersProviderImpl : SchedulersProvider {

    override val mainThread: Scheduler = Schedulers.mainScheduler
    override val ioThread: Scheduler = Schedulers.ioScheduler
    override val computationThread: Scheduler = Schedulers.computationScheduler
}