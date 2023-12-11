package ru.ok.itmo.example.domain

import android.util.Log
import ru.ok.itmo.example.enter.login.LogInResult
import ru.ok.itmo.example.network.ResponseCode
import ru.ok.itmo.example.network.TamTamApi
import ru.ok.itmo.example.network.dto.LoginRequest
import java.io.IOException

class TamTamUseCase(private val tamTamApi: TamTamApi) {
    companion object {
        const val TAG = "TamTamUseCase"
    }

    suspend fun logIn(login: String, password: String): LogInResult {
        val response = try {
            tamTamApi.logIn(LoginRequest(login, password)).execute()
        } catch (e: IOException) {
            Log.d(TAG, "Error: ${e.message}")
            return LogInResult.failure(LogInResult.ErrorType.NO_INTERNET_CONNECTION)
        }
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                return LogInResult.success(response.body()!!)
            }
            Log.d(TAG, "Unexpected error: response body is null.")
            return LogInResult.failure(LogInResult.ErrorType.UNKNOWN_ERROR)
        }
        return when (response.code()) {
            ResponseCode.UNAUTHORIZED -> LogInResult.failure(LogInResult.ErrorType.INVALID_LOGIN_OR_PASSWORD)
            ResponseCode.TIMED_OUT -> LogInResult.failure(LogInResult.ErrorType.TIMED_OUT)
            else -> LogInResult.failure(LogInResult.ErrorType.UNKNOWN_ERROR)
        }
    }

    fun logout(token: String) {
        try {
            tamTamApi.logout(token).execute()
        } catch (e: IOException) {
            Log.d(TAG, "Error: ${e.message}")
        }
    }
}