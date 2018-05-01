package io.github.aleksandersh.mysocialnetworkphotos.domain.repository

import io.github.aleksandersh.mysocialnetworkphotos.domain.model.AuthorizationProperties

interface AuthorizationRepository {

    fun getAuthorizationProperties(): AuthorizationProperties

    fun checkUrlHandling(url: String): Boolean

    fun checkUrlForLoginAndHandleSession(url: String): Boolean

    fun checkUrlForCancel(url: String): Boolean
}