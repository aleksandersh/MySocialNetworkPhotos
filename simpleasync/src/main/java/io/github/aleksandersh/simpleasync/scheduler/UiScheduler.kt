package io.github.aleksandersh.simpleasync.scheduler

import android.os.Handler
import android.os.Looper

class UiScheduler : Scheduler {

    private val handler = Handler(Looper.getMainLooper())

    override fun post(runnable: Runnable) {
        handler.post(runnable)
    }
}