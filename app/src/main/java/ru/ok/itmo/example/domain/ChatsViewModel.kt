package ru.ok.itmo.example.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChatsViewModel : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            AuthorizationStorage.logout()
        }
    }

}
