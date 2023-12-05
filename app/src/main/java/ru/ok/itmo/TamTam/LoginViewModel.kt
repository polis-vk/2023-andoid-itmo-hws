package ru.ok.itmo.TamTam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> get() = _loginResult

    fun checkLogin(user : User) {
        Log.d("checkLog", "1231")
        viewModelScope.launch(Dispatchers.IO) {
            val result = UserRepository.login(user)
            _loginResult.postValue(result)
        }
    }
}