package io.github.aleksandersh.mysocialnetworkphotos.data.repository

import io.github.aleksandersh.mysocialnetworkphotos.data.datasource.SessionHttpDatasource
import io.github.aleksandersh.mysocialnetworkphotos.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val sessionHttpDatasource: SessionHttpDatasource
) : SessionRepository {

    override fun checkSession(): Boolean {
        return sessionHttpDatasource.checkSession()
    }
}