package ru.ok.itmo.tamTam.chats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ok.itmo.tamTam.AuthInfo
import ru.ok.itmo.tamTam.chats.models.ChatPreview
import ru.ok.itmo.tamTam.chats.models.Message
import ru.ok.itmo.tamTam.login.LoginRepository
import ru.ok.itmo.tamTam.login.LoginRequest
import ru.ok.itmo.tamTam.login.User
import ru.ok.itmo.tamTam.serverService




object Repository {
    suspend fun messages(): Result<MutableList<Message>>{
        return try {
            val response = serverService.messages(AuthInfo.login, AuthInfo.token)

            val listType = object : TypeToken<List<Message>>() {}.type
            val messageList: MutableList<Message> = Gson().fromJson(response.string(), listType)



            Log.d("RESPONSE", messageList.toString())

            Result.success(messageList.toMutableList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
