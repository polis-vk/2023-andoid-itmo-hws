package ru.ok.itmo.example.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainRepository {

    val server = "https://faerytea.name:8008"
    var user: User? = null

    val channels = MutableLiveData<Array<String>>()

    fun buildURL(vararg paths: String): URL {
        return Uri.parse(server).buildUpon().run {
            paths.forEach { appendPath(it) }
            URL(toString())
        }
    }

    fun updateChannels(callback: (Exception?, Long)->Unit) {
        val url = buildURL("channels")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                (url.openConnection() as HttpURLConnection).run {
                    requestMethod = "GET"

                    val code = responseCode
                    val response = inputStream.bufferedReader().readText()

                    launch(Dispatchers.Main) {
                        if (code == 200) {
                            val jsonArray = JSONArray(response)

                            launch(Dispatchers.Main) {
                                channels.value = Array(jsonArray.length()) {
                                    jsonArray.getString(it).dropLast(8)
                                }
                            }
                        } else {
                            callback(null, System.currentTimeMillis())
                        }
                    }
                }
            } catch (e: IOException) {
                launch(Dispatchers.Main) {
                    callback(null, System.currentTimeMillis())
                }
            }
        }
    }

    fun verifyAndLogin(
        login: String,
        password: String,
        signIn: MutableLiveData<Boolean>,
        errorCallback: (Int?, Exception?)->Unit
    ) {
        val requestBody = "{ \"name\": \"$login\",  \"pwd\": \"$password\" }".toByteArray()
        val url = buildURL("login")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                (url.openConnection() as HttpURLConnection).run {
                    requestMethod = "POST"
                    outputStream.write(requestBody)

                    val code = responseCode
                    val token = inputStream.bufferedReader().readLine()

                    launch(Dispatchers.Main) {
                        if (code == 200) {
                            launch(Dispatchers.Main) {
                                user = User(token)
                                signIn.value = true
                            }
                        } else {
                            errorCallback(code, null)
                        }
                    }
                }
            } catch (e: IOException) {
                launch(Dispatchers.Main) {
                    errorCallback(null, e)
                }
            }
        }
    }
}