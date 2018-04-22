package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField

class FriendsViewState {

    val contentScreen = ObservableField<Boolean>()
    val zeroScreenData = ObservableField<ZeroScreenData>()
    val items = ObservableField<List<Friend>>()
}