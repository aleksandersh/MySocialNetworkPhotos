package io.github.aleksandersh.simplemvp

import android.os.Bundle
import android.support.v4.app.Fragment

class HolderFragment : Fragment() {

    val presenterStore = PresenterStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterStore.clear()
    }
}