package ru.ok.itmo.example

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel: ViewModel() {

    val signedIn = MutableLiveData<Boolean>()
//    val selectedItem: LiveData<Int> get() = signedIn


    val server = "https://faerytea.name:8008"


    fun verifyAndLogin(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("model", "verify $login $password")
            val requestBody = "{ \"name\": \"$login\",  \"pwd\": \"$password\" }".toByteArray()

            val url = URL("${server}/login")
            (url.openConnection() as HttpURLConnection)!!.run {
                requestMethod = "POST"
                doOutput = true
                outputStream.write(requestBody)
                outputStream.flush()
                outputStream.close()

                if (responseCode == 200) {
                    launch(Dispatchers.Main) {
                        signedIn.value = true
                    }
                } else {
                    // pass
                }
            }

        }
    }
}