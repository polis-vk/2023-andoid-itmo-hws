package ru.ok.itmo.example.login

import ru.ok.itmo.example.server.ServerWorker

class LoginModel {
    suspend fun authorization(
        login: String,
        password: String,
        onDataReadyCallback: OnDataReadyCallback
    ) {
        ServerWorker.login(login, password).collect { token ->
            onDataReadyCallback.onDataReady(token)
        }
    }
}

interface OnDataReadyCallback {
    fun onDataReady(data: String)
}