package ru.ok.itmo.example.domain

sealed interface LoginState {
    data class Success(val token: String?) : LoginState
    data class Failure(val throwable: Throwable) : LoginState
    data object Unknown : LoginState
}
