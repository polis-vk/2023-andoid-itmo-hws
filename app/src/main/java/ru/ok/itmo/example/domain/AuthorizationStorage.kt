package ru.ok.itmo.example.domain

import ru.ok.itmo.example.client.AuthorizationProvider
import ru.ok.itmo.example.dto.UserAuthorization

object AuthorizationStorage {
    private val authorizationProvider = AuthorizationProvider()
    private var token: String? = null

    suspend fun login(userAuthorization: UserAuthorization): Result<String?> {
        val result = authorizationProvider.login(userAuthorization)
        token = result.getOrNull()
        return result
    }

    suspend fun logout() {
        authorizationProvider.logout(token)
    }
}
