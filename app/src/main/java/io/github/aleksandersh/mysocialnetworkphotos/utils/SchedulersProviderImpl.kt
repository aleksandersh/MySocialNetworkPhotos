package io.github.aleksandersh.mysocialnetworkphotos.utils

import io.github.aleksandersh.simpleasync.Schedulers
import io.github.aleksandersh.simpleasync.scheduler.Scheduler

class SchedulersProviderImpl : SchedulersProvider {

    override val mainThread: Scheduler = Schedulers.mainScheduler
    override val backgroundThread: Scheduler = Schedulers.backgroundScheduler
}