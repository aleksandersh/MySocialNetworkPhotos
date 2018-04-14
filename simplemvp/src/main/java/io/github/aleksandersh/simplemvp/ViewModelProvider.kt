package io.github.aleksandersh.simplemvp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

class ViewModelProvider {

    companion object {

        private const val HOLDER_FRAGMENT_TAG = "view_model_holder_fragment"
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> getViewModel(
        fragment: Fragment,
        key: String,
        factory: ViewModelFactory<T>
    ): T {
        return getHolderFragment(fragment)
            .viewModelStore
            .let { getViewModel(it, key, factory) } as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> getViewModel(
        activity: FragmentActivity,
        key: String,
        factory: ViewModelFactory<T>
    ): T {
        return getHolderFragment(activity)
            .viewModelStore
            .let { getViewModel(it, key, factory) } as T
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

    private fun getViewModel(
        holderFragment: ViewModelStore,
        key: String,
        factory: ViewModelFactory<*>
    ): ViewModel {
        val viewModel = holderFragment.get(key)
        if (viewModel != null) {
            return viewModel
        }

        val newViewModel = factory.create()
        holderFragment.put(key, newViewModel)
        return newViewModel
    }
}