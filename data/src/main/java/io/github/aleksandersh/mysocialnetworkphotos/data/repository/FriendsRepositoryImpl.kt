package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.datasource.FriendsHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.domain.model.FriendsResult
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.FriendsRepository

class FriendsRepositoryImpl(
    private val friendsHttpDatasource: FriendsHttpDatasource
) : FriendsRepository {

    override fun getFriends(offset: Int, count: Int): FriendsResult {
        return friendsHttpDatasource.getFriends(offset, count)
    }
}