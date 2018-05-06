package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.FriendsResult

interface FriendsRepository {

    fun getFriends(offset: Int, count: Int): FriendsResult
}