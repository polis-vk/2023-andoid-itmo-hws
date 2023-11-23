package ru.ok.itmo.tamtam.model

import kotlinx.coroutines.flow.Flow
import ru.ok.itmo.tamtam.login.OnDataReadyCallback
import ru.ok.itmo.tamtam.server.ServerWorker

class Model {
    suspend fun authorization(
        login: String,
        password: String,
        onDataReadyCallback: OnDataReadyCallbackBoolean
    ) {
        ServerWorker.login(login, password).collect { token ->


            onDataReadyCallback.onDataReady(true)
        }
    }


    suspend fun getMessage(
        token: String,
        onDataReadyCallback: OnDataReadyCallbackString
    ) {
        ServerWorker.getMessages(token).collect { response ->
            onDataReadyCallback.onDataReady(response)
        }
    }
}

interface OnDataReadyCallbackBoolean {
    fun onDataReady(data: Boolean)
}

interface OnDataReadyCallbackString {
    fun onDataReady(data: String)
}