package io.github.aleksandersh.mysocialnetworkphotos.domain.model

data class AuthorizationProperties(
    val host: String,
    val clientId: String,
    val apiVersion: String,
    val state: String,
    val authorizationUri: String,
    val redirectUri: String,
    val authorizationQuery: String
)