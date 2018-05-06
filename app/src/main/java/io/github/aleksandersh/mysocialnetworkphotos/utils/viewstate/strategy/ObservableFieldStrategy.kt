package io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy

import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField

interface ObservableFieldStrategy<T> {

    fun onSubscribe(
        currentValue: T?,
        observer: ObservableField<T>.FieldObserver<T>,
        subscriptions: ArrayList<ObservableField<T>.FieldObserver<T>>
    ): T?

    fun onValueChanged(
        oldValue: T?,
        newValue: T,
        subscriptions: ArrayList<ObservableField<T>.FieldObserver<T>>
    ): T?
}