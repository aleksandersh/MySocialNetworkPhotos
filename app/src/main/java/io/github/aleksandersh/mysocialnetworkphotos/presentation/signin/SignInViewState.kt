package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField

class SignInViewState {

    val error = ObservableField<String>()
    val progress = ObservableField<Boolean>()
    val complete = ObservableField<Boolean>()
}