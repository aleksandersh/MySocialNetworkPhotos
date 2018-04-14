package io.github.aleksandersh.simplemvp

class PresenterStore {

    private val storedPresenters: HashMap<String, Presenter> = HashMap()

    fun put(key: String, presenter: Presenter) {
        val stale = storedPresenters.put(key, presenter)
        stale?.onDestroy()
    }

    fun get(key: String): Presenter? {
        return storedPresenters[key]
    }

    fun clear() {
        storedPresenters.forEach { it.value.onDestroy() }
        storedPresenters.clear()
    }
}