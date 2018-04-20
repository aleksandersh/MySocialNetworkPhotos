package io.github.aleksandersh.mysocialnetworkphotos.dependencies

abstract class Branch<P, T>(private val parent: TreeElement<P>) : TreeElement<T> {

    private var componentInstance: T? = null
    private val uses: HashSet<String> = HashSet()

    protected abstract fun create(parent: P): T

    override fun provide(tag: String): T {
        val current = componentInstance
        if (uses.add(tag) || current == null) {
            val parentComponent = parent.provide(tag)
            if (current == null) {
                val newComponent = create(parentComponent)
                componentInstance = newComponent
                return newComponent
            }
        }
        return current
    }

    override fun release(tag: String) {
        uses.remove(tag)
        if (uses.isEmpty()) {
            componentInstance = null
        }
        parent.release(tag)
    }
}