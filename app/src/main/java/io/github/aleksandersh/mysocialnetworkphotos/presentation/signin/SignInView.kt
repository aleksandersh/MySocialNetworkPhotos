package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

/**
 * Created by aleksandersh on 3/31/18.
 */
interface SignInView {

    companion object {

        const val TAG = "SignInFragment"
    }

    fun showLoading(loading: Boolean)
}