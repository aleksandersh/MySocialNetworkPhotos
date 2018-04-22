package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.Friend
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.FriendsRepository

class FriendsInteractor(private val friendsRepository: FriendsRepository) {

    fun getFriends(): List<Friend> {
        return friendsRepository.getFriends()
    }
}