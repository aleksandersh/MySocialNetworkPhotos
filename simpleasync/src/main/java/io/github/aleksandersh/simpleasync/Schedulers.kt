package io.github.aleksandersh.simpleasync

import android.os.Looper
import io.github.aleksandersh.simpleasync.scheduler.*

object Schedulers {

    val backgroundScheduler: Scheduler by lazy(::BackgroundScheduler)

    val mainScheduler: Scheduler by lazy(::UiScheduler)

    val currentThread: Scheduler by lazy(::CurrentThreadScheduler)

    fun fromLooper(looper: Looper): Scheduler = CustomScheduler(looper)
}