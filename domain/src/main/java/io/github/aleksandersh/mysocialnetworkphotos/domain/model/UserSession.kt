package io.github.aleksandersh.mysocialnetworkphotos.domain.model

import java.util.*

data class UserSession(
    val userId: Long,
    val token: String,
    val expires: Date
)