package io.github.aleksandersh.mysocialnetworkphotos.domain.model

data class Friend(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val smallPhotoUrl: String,
    val bigPhotoUrl: String
)