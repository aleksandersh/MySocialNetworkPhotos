package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.mysocialnetworkphotos.utils.ResourceManager

/**
 * Created by aleksandersh on 3/31/18.
 */
class SignInPresenter(private val resourceManager: ResourceManager, private val view: SignInView) {

    private var loading = false

    fun onClickLogIn(login: CharSequence, password: CharSequence) {
        loading = !loading
        view.showLoading(loading)
    }
}