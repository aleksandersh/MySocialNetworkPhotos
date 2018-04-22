package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend

interface FriendsRepository {

    fun getFriends(): List<Friend>
}