package io.github.aleksandersh.simpleasync

import android.os.Looper
import io.github.aleksandersh.simpleasync.scheduler.*

object Schedulers {

    @Deprecated("Use ioScheduler or computationScheduler instead")
    val backgroundScheduler: Scheduler by lazy(::BackgroundScheduler)

    val mainScheduler: Scheduler by lazy(::UiScheduler)

    val ioScheduler: Scheduler by lazy(::IoScheduler)

    val computationScheduler: Scheduler by lazy(::ComputationScheduler)

    val currentThread: Scheduler by lazy(::CurrentThreadScheduler)

    fun fromLooper(looper: Looper): Scheduler = CustomScheduler(looper)
}