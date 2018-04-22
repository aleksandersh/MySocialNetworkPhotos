package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.FriendsRepository

class FriendsRepositoryImpl : FriendsRepository {

    override fun getFriends(): List<Friend> {
        return emptyList()
    }
}