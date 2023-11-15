package ru.ok.itmo.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Unknown)
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun login(login: String, password: String) {
        viewModelScope.launch {
            AuthorizationProvider.login(UserAuthorization(login, password)).onSuccess {
                _loginState.value = LoginState.Success(it)
            }.onFailure {
                _loginState.value = LoginState.Failure(it)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _loginState.value = LoginState.Unknown
        }
    }

}