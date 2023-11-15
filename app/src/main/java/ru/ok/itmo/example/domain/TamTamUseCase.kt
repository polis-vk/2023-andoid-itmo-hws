package ru.ok.itmo.example.domain

import ru.ok.itmo.example.enter.login.LogInResult
import ru.ok.itmo.example.network.ResponseCode
import ru.ok.itmo.example.network.TamTamApi
import ru.ok.itmo.example.network.dto.LoginRequest

class TamTamUseCase(private val tamTamApi: TamTamApi) {
    suspend fun logIn(login: String, password: String): LogInResult {
        val response = tamTamApi.logIn(LoginRequest(login, password)).execute()
        if (response.isSuccessful) {
            return LogInResult.success(response.body()!!)
        }
        if (response.code() == ResponseCode.UNAUTHORIZED) {
            return LogInResult.failure(LogInResult.ErrorType.INVALID_LOGIN_OR_PASSWORD)
        }
        return when(response.code()) {
            ResponseCode.UNAUTHORIZED -> LogInResult.failure(LogInResult.ErrorType.INVALID_LOGIN_OR_PASSWORD)
            ResponseCode.TIMED_OUT -> LogInResult.failure(LogInResult.ErrorType.TIMED_OUT)
            else -> LogInResult.failure(LogInResult.ErrorType.UNKNOWN_ERROR)
        }
    }

    fun logout(token: String) {
        tamTamApi.logout(token)
    }
}