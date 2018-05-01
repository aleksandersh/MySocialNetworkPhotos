package io.github.aleksandersh.mysocialnetworkphotos.domain.usecase

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.FriendsResult
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.FriendsRepository

class FriendsInteractor(private val friendsRepository: FriendsRepository) {

    companion object {

        private const val ITEMS_PER_PAGE = 30
    }

    fun getFriends(pageNumber: Int): FriendsResult {
        return friendsRepository.getFriends(pageNumber * ITEMS_PER_PAGE, ITEMS_PER_PAGE)
    }
}