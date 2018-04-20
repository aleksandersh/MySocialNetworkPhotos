package io.github.aleksandersh.mysocialnetworkphotos.dependencies

abstract class Root<T>(private val component: T) : TreeElement<T> {

    override fun provide(tag: String): T {
        return component
    }

    override fun release(tag: String) {
    }
}