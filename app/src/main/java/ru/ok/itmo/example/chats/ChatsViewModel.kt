package ru.ok.itmo.example.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ok.itmo.example.domain.TamTamUseCase
import ru.ok.itmo.example.network.RetrofitProvider
import ru.ok.itmo.example.network.TamTamApi

class ChatsViewModel : ViewModel() {

    private val tamTamUseCase by lazy {
        val retrofit = RetrofitProvider.retrofit
        val tamTamApi = TamTamApi.provideTamTamApi(retrofit)
        TamTamUseCase(tamTamApi)
    }
    fun logout(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tamTamUseCase.logout(token)
        }
    }
}