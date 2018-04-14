package io.github.aleksandersh.simpleasync.scheduler

class CurrentThreadScheduler : Scheduler {

    override fun post(runnable: Runnable) {
        runnable.run()
    }
}