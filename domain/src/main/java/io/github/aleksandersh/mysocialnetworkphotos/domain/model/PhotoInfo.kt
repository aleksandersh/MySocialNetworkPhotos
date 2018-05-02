package io.github.aleksandersh.mysocialnetworkphotos.domain.model

import java.util.*

data class PhotoInfo(
    val id: Long,
    val ownerId: Long,
    val albumId: Long,
    val text: String,
    val date: Date,
    val likeCount: Int,
    val commentCount: Int
)