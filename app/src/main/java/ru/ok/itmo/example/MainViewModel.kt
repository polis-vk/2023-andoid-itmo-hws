package ru.ok.itmo.example


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel: ViewModel() {

    val signIn = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<Int?>()

    val server = "https://faerytea.name:8008"

    fun verifyAndLogin(login: String, password: String) {
        val requestBody = "{ \"name\": \"$login\",  \"pwd\": \"$password\" }".toByteArray()
        val url = URL("${server}/login")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                (url.openConnection() as HttpURLConnection)!!.run {
                    requestMethod = "POST"
                    outputStream.write(requestBody)

                    val code = responseCode

                    launch(Dispatchers.Main) {
                        if (code == 200 || password=="asd") { // TODO: remove debug password
                            launch(Dispatchers.Main) {
                                signIn.value = true
                            }
                        } else {
                            showErrorMessage(code)
                        }
                    }
                }
            } catch (e: IOException) {
                launch(Dispatchers.Main) {
                    toastMessage.value = R.string.login_error_cant_connect
                }
            }
        }
    }

    fun showErrorMessage(responseCode: Int) {
        toastMessage.value = when (responseCode) {
            401 -> R.string.login_error_unauthorized
            else -> R.string.login_error
        }
    }
}