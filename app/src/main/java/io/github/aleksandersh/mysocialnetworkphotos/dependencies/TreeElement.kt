package io.github.aleksandersh.mysocialnetworkphotos.dependencies

interface TreeElement<T> {

    fun provide(tag: String): T

    fun release(tag: String)
}