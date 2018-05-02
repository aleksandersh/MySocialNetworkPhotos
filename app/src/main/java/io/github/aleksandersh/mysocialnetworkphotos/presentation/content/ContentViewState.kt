package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy.HandleOnceStrategy

class ContentViewState {

    val screen = ObservableField<Screen>(HandleOnceStrategy())
    val contentScreen = ObservableField<Boolean>()
    val zeroScreenData = ObservableField<ZeroScreenData>()
}