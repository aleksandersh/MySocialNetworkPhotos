package io.github.aleksandersh.mysocialnetworkphotos.presentation.friends

import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.AdapterNotifier
import io.github.aleksandersh.mysocialnetworkphotos.presentation.base.model.ZeroScreenData
import io.github.aleksandersh.mysocialnetworkphotos.presentation.friends.model.FriendsListItem
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.ObservableField
import io.github.aleksandersh.mysocialnetworkphotos.utils.viewstate.strategy.NotifySubscribersStrategy

class FriendsViewState {

    val contentScreen = ObservableField<Boolean>()
    val zeroScreenData = ObservableField<ZeroScreenData>()
    val items = ObservableField<List<FriendsListItem>>()
    val adapterNotifiers = ObservableField<AdapterNotifier>(NotifySubscribersStrategy())
}