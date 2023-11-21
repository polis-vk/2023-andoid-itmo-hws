package ru.ok.itmo.example.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ok.itmo.example.login.repository.FakeLoginRepository
import ru.ok.itmo.example.login.repository.LoginState
import ru.ok.itmo.example.login.repository.UserCredentials

class LoginViewModel : ViewModel() {
    private val loginRepository = FakeLoginRepository();

    private val _status = MutableStateFlow<LoginState>(LoginState.Started)
    val status = _status.asStateFlow()

    fun login(login: String, password: String) {
        Log.d(LoginFragment.TAG, "login: $login, password: $password")
        viewModelScope.launch {
            _status.emit(LoginState.Loading)
            loginRepository.login(UserCredentials(login, password)).onSuccess {
                Log.d(LoginFragment.TAG, "AccessToken: $it")
                _status.emit(LoginState.Success(it))
            }.onFailure {
                Log.d(LoginFragment.TAG, "Error: $it")
                _status.emit(LoginState.Failure(it))
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _status.emit(LoginState.Started)
        }
    }
}