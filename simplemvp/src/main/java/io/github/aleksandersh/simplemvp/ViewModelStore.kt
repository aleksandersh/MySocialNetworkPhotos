package io.github.aleksandersh.simplemvp

class ViewModelStore {

    private val storedViewModels: HashMap<String, ViewModel> = HashMap()

    fun put(key: String, viewModel: ViewModel) {
        val oldViewModel = storedViewModels.put(key, viewModel)
        oldViewModel?.onDestroy()
    }

    fun get(key: String): ViewModel? {
        return storedViewModels[key]
    }

    fun clear() {
        storedViewModels.forEach { it.value.onDestroy() }
        storedViewModels.clear()
    }
}