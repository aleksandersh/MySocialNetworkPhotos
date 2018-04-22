package io.github.aleksandersh.mysocialnetworkphotos.presentation.signin

import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy.HandleOnceStrategy

class SignInViewState {

    val progress = ObservableField<Boolean>()
    val complete = ObservableField<Boolean>()
    val cancel = ObservableField<Boolean>()
    val zeroScreenShowed = ObservableField<Boolean>()
    val zeroScreenData = ObservableField<ZeroScreenData>()
    val loadUrl = ObservableField<String>(HandleOnceStrategy())
}