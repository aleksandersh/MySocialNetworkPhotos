package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.storage.FriendsHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.FriendsRepository

class FriendsRepositoryImpl(
    private val friendsHttpDatasource: FriendsHttpDatasource
) : FriendsRepository {

    override fun getFriends(): List<Friend> {
        return friendsHttpDatasource.getFriends()
    }
}