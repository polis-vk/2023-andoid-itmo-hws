package ru.ok.itmo.example.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.example.chats.repository.ChatsRepository
import ru.ok.itmo.example.chats.repository.ImageState
import ru.ok.itmo.example.chats.retrofit.models.ChannelId
import ru.ok.itmo.example.chats.retrofit.models.Data
import ru.ok.itmo.example.chats.retrofit.models.Message
import ru.ok.itmo.example.chats.retrofit.models.Text
import ru.ok.itmo.example.login.repository.UserDataRepository
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatsRepository: ChatsRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    private val _messages = MutableStateFlow<MessagesState>(MessagesState.Loading)

    val messages = _messages.asStateFlow()
    private val _images = MutableStateFlow<ImageState>(ImageState.Started)

    val images = _images.asStateFlow()

    fun getMessages(channelId: ChannelId) {
        viewModelScope.launch(Dispatchers.IO) {
            chatsRepository.getMessages(channelId)
                .onStart { _messages.emit(MessagesState.Loading) }
                .catch {
                    withContext(Dispatchers.Main) {
                        _messages.emit(
                            MessagesState.Failure(
                                RuntimeException("Message getting error")
                            )
                        )
                    }
                }
                .collect {
                    _messages.emit(MessagesState.Success(it))
                }
        }
    }

    fun getUserMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            _messages.emit(MessagesState.Loading)
            chatsRepository.getUserMessages(
                userDataRepository.getToken(),
                userDataRepository.getLogin()
            )
                .onStart { _messages.emit(MessagesState.Loading) }
                .catch {
                    withContext(Dispatchers.Main) {
                        _messages.emit(
                            MessagesState.Failure(
                                RuntimeException("Message getting error")
                            )
                        )
                    }
                }
                .collect {
                    _messages.emit(MessagesState.Success(it))
                }
        }
    }

    fun getImage(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatsRepository.getImage(url)
                .onStart { _images.emit(ImageState.Loading) }
                .collect { _images.emit(ImageState.Success(it)) }
        }
    }

    fun createNewChat(chatName: String = "kok1 abobus") {
        val greetingMessage =
            Message(
                "",
                userDataRepository.getLogin(),
                chatName,
                Data(Text("Hello $chatName"), null),
                ""
            )

        viewModelScope.launch(Dispatchers.IO) {
            chatsRepository.sendMessage(userDataRepository.getToken(), greetingMessage)
                .catch {  }
        }
    }

    fun openChat(chatName: String) {
        userDataRepository.openChat(chatName)
    }

    fun getCurrentUser(): String {
        return userDataRepository.getLogin()
    }
}

sealed interface MessagesState {
    class Success(val messages: List<Message>) : MessagesState
    class Failure(val error: Throwable) : MessagesState
    object Loading : MessagesState
}
