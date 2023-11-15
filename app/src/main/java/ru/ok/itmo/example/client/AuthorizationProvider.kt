package ru.ok.itmo.example.client

import ru.ok.itmo.example.domain.ErrorType
import ru.ok.itmo.example.dto.UserAuthorization

class AuthorizationProvider {
    private fun client() = LoginApi.provideLoginApi(RetrofitProvider.retrofit(""))

    suspend fun login(userAuthorization: UserAuthorization): Result<String?> {
        return try {
            val result = runCatching {
                client().login(userAuthorization)
            }
            val response = result.getOrNull() ?: return Result.failure(ErrorType.Unknown())
            return when (response.code()) {
                200 -> Result.success(response.body())
                401 -> Result.failure(ErrorType.Unauthorized())
                else -> Result.failure(ErrorType.Unknown())
            }
        } catch (e: Exception) {
            Result.failure(ErrorType.InternetConnection())
        }
    }

    suspend fun logout(token: String?) {
        runCatching {
            client().logout()
        }
    }
}
