package io.github.aleksandersh.mysocialnetworkphotos.presentation.content

import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy.HandleOnceStrategy

class ContentViewState {

    val screen = ObservableField<Screen>(HandleOnceStrategy())
}