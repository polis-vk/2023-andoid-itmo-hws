package ru.ok.itmo.tuttut.chats.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ok.itmo.tuttut.chats.domain.ChatUI
import ru.ok.itmo.tuttut.chats.domain.ChatsRepository
import ru.ok.itmo.tuttut.chats.domain.ChatsState
import ru.ok.itmo.tuttut.messenger.domain.Chat
import ru.ok.itmo.tuttut.messenger.domain.ChatsDAO
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatsDAO: ChatsDAO,
    private val chatsRepository: ChatsRepository
) : ViewModel() {

    private val _chatsState = MutableStateFlow<ChatsState>(ChatsState.Loading)
    val chatsState: StateFlow<ChatsState> = _chatsState.asStateFlow()

    fun getChats() {
        viewModelScope.launch {
            _chatsState.emit(
                ChatsState.Success(
                    chatsDAO.getAll().map(::chatToUI)
                )
            )
            chatsRepository.getChats().onSuccess {
                chatsDAO.insert(it)
                _chatsState.emit(
                    ChatsState.Success(it.map(::chatToUI))
                )
            }.onFailure {
                Log.e("Chats", it.toString())
            }
        }
    }

    companion object {
        fun chatToUI(chat: Chat): ChatUI {
            val (message, time) = chat.messages.lastOrNull()?.let {
                it.data.text?.text to
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(
                            Instant.ofEpochMilli(it.time)
                        )
            } ?: (null to null)

            return ChatUI(
                chat.name,
                message,
                time
            )
        }
    }
}