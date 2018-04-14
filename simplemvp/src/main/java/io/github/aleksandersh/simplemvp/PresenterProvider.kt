package io.github.aleksandersh.simplemvp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

class PresenterProvider {

    companion object {

        private const val HOLDER_FRAGMENT_TAG = "simplemvp_holder_fragment"
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Presenter> provide(
        fragment: Fragment,
        key: String,
        factory: PresenterFactory<T>
    ): T {
        return getHolderFragment(fragment)
            .presenterStore
            .let { provide(it, key, factory) } as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Presenter> provide(
        activity: FragmentActivity,
        key: String,
        factory: PresenterFactory<T>
    ): T {
        return getHolderFragment(activity)
            .presenterStore
            .let { provide(it, key, factory) } as T
    }

    private fun getHolderFragment(activity: FragmentActivity): HolderFragment {
        return activity.supportFragmentManager
            .let(::getHolderFragment)
    }

    private fun getHolderFragment(fragment: Fragment): HolderFragment {
        return fragment.childFragmentManager
            .let(::getHolderFragment)
    }

    private fun getHolderFragment(fragmentManager: FragmentManager): HolderFragment {
        val holderFragment = findHolderFragment(fragmentManager)
        if (holderFragment != null) {
            return holderFragment
        }
        return createHolderFragment(fragmentManager)
    }

    private fun findHolderFragment(fragmentManager: FragmentManager): HolderFragment? {
        return fragmentManager.findFragmentByTag(HOLDER_FRAGMENT_TAG) as HolderFragment?
    }

    private fun createHolderFragment(fragmentManager: FragmentManager): HolderFragment {
        val holderFragment = HolderFragment()
        fragmentManager.beginTransaction()
            .add(
                holderFragment,
                HOLDER_FRAGMENT_TAG
            )
            .commit()
        return holderFragment
    }

    private fun provide(
        holderFragment: PresenterStore,
        key: String,
        factory: PresenterFactory<*>
    ): Presenter {
        val presenter = holderFragment.get(key)
        if (presenter != null) {
            return presenter
        }

        val newPresenter = factory.create()
        holderFragment.put(key, newPresenter)
        return newPresenter
    }
}