package io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy

import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField

class HandleOnceStrategy<T> :
    ObservableFieldStrategy<T> {

    override fun onSubscribe(
        currentValue: T?,
        observer: ObservableField<T>.FieldObserver<T>,
        subscriptions: ArrayList<ObservableField<T>.FieldObserver<T>>
    ): T? {
        currentValue?.let(observer.callback)
        return null
    }

    override fun onValueChanged(
        oldValue: T?,
        newValue: T,
        subscriptions: ArrayList<ObservableField<T>.FieldObserver<T>>
    ): T? {
        subscriptions.forEach { it.callback(newValue) }
        return null
    }
}