package io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy.ObservableFieldStrategy
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy.SimpleStrategy

class ObservableField<T>(private val strategy: ObservableFieldStrategy<T> = SimpleStrategy()) {

    private val subscriptions = ArrayList<FieldObserver<T>>()

    private var currentValue: T? = null

    fun set(value: T) {
        currentValue = strategy.onValueChanged(currentValue, value, subscriptions)
    }

    fun subscribe(lifecycleOwner: LifecycleOwner, callback: (T) -> Unit) {
        val observer = FieldObserver(lifecycleOwner, callback)
        lifecycleOwner.lifecycle.addObserver(observer)
        subscriptions.add(observer)
        currentValue = strategy.onSubscribe(currentValue, observer, subscriptions)
    }

    private fun unsubscribe(fieldObserver: FieldObserver<*>) {
        fieldObserver.lifecycleOwner.lifecycle.removeObserver(fieldObserver)
        val iterator = subscriptions.iterator()
        while (iterator.hasNext()) {
            val subscription = iterator.next()
            if (subscription == fieldObserver) {
                iterator.remove()
                break
            }
        }
    }

    inner class FieldObserver<T>(
        val lifecycleOwner: LifecycleOwner,
        val callback: (T) -> Unit
    ) : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            unsubscribe(this)
        }
    }
}