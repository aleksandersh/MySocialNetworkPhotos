package io.github.aleksandersh.simpleasync.scheduler

import android.os.Handler
import android.os.HandlerThread

class BackgroundScheduler : Scheduler {

    companion object {

        const val THREAD_NAME = "SimpleAsync_BackgroundScheduler"
    }

    private val handlerThread: HandlerThread by lazy(::setupHandlerThread)
    private val handler: Handler by lazy(::setupHandler)

    override fun post(runnable: Runnable) {
        handler.post(runnable)
    }

    private fun setupHandlerThread(): HandlerThread {
        val thread = HandlerThread(THREAD_NAME)
        thread.start()
        return thread
    }

    private fun setupHandler(): Handler {
        return Handler(handlerThread.looper)
    }
}