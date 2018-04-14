package io.github.aleksandersh.simplemvp

interface PresenterFactory<out T : Presenter> {

    fun create(): T
}