package io.github.aleksandersh.simpleasync.scheduler

import android.os.Handler
import android.os.Looper

class CustomScheduler(looper: Looper) : Scheduler {

    private val handler = Handler(looper)

    override fun post(runnable: Runnable) {
        handler.post(runnable)
    }
}