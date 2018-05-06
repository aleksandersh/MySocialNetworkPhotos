package io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy

import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField

/**
 * Отправляет значение только при изменении и только текущим подписчикам.
 */
class NotifySubscribersStrategy<T> : ObservableFieldStrategy<T> {

    override fun onSubscribe(
        currentValue: T?,
        observer: ObservableField<T>.FieldObserver<T>,
        subscriptions: ArrayList<ObservableField<T>.FieldObserver<T>>
    ): T? {
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