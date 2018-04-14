package io.github.aleksandersh.simplemvp

interface ViewModelFactory<out T : ViewModel> {

    fun create(): T
}