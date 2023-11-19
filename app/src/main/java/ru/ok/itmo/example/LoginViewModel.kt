package ru.ok.itmo.example

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.ok.itmo.example.Retrofit.LoginRequest
import ru.ok.itmo.example.Retrofit.MainApi
import ru.ok.itmo.example.Retrofit.RetrofitInitialization

class LoginViewModel: ViewModel() {
    var token = ""
    val isEnabledLive = MutableLiveData(false)
    var loginLive = MutableLiveData("")
    var pwdLive = MutableLiveData("")
    var navigationLive = MutableLiveData(0)

    private var loginRequest = LoginRequest("", "")
    private var internetStatus = false

    private val mainApi = RetrofitInitialization().getClient().create(MainApi::class.java);

    fun editLogin(string: String){
        this.loginLive.value = string
        Log.i("LoginVM", "new loginLive: '${loginLive.value}'")
        isEnabled()
    }


    fun editPwd(string: String){
        this.pwdLive.value = string
        Log.i("LoginVM", "new pwdLive: '${pwdLive.value}'")
        isEnabled()
    }

    fun checkInternet(flag: Boolean){
        this.internetStatus = flag;
    }

    fun formLR(){
        this.loginRequest = LoginRequest(this.loginLive.value, this.pwdLive.value)
    }

    fun isEnabled(){
        isEnabledLive.value = !loginLive.value!!.isEmpty() && !pwdLive.value!!.isEmpty()
    }



    fun auth() {
        formLR();
        viewModelScope.launch(Dispatchers.IO) {
            val logInResponse = mainApi.logIn(loginRequest)
            logInResponse.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body() != null) {
                        viewModelScope.launch (Dispatchers.Main){
                            token = response.body().toString();
                        }
                        navigationLive.value = 200
                    } else {
                        navigationLive.value = response.code();
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    if (!internetStatus) {
                        navigationLive.value = 402
                    } else {
                        navigationLive.value = 403
                    }
                }
            })

        }
    }

    fun logout() {

        viewModelScope.launch(Dispatchers.IO) {
            mainApi.logout(token)
        }
    }
}