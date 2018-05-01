package io.github.aleksandersh.mysocialnetworkphotos.domain.model

data class FriendsResult(
    val friends: List<Friend>,
    val totalCount: Int,
    val contentFinished: Boolean
)