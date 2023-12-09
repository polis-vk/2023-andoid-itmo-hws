package ru.ok.itmo.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {
    private val loginManager = LoginManager()
    private var authTokenData: AuthTokenData? = null

    private val _state = MutableStateFlow<LoginState>(LoginState.Started)
    val state = _state.asStateFlow()


    fun login(loginData: LoginData) {
        viewModelScope.launch {
            _state.emit(LoginState.Started)
            loginManager.login(loginData).onSuccess {
                authTokenData = it
                _state.emit(LoginState.Success(it))
            }.onFailure {
                _state.emit(LoginState.Error(it))
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                loginManager.logout(authTokenData!!)
            } catch (e: Throwable) {
                _state.emit(LoginState.Error(e))
            }
        }
    }

}