package ru.ok.itmo.tamtam.domain.model

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ok.itmo.tamtam.domain.CommunicationStorage
import ru.ok.itmo.tamtam.domain.state.CommunicationState
import ru.ok.itmo.tamtam.dto.Message
import ru.ok.itmo.tamtam.util.TextPresentObjects
import java.text.SimpleDateFormat
import java.util.Date

class CommunicationViewModel : ViewModel() {
    data class MessageInfo(val author: String?, val text: String?, val time: String?)

    private val _chatsState = MutableLiveData<CommunicationState>(CommunicationState.Unknown)
    val chatsState: LiveData<CommunicationState>
        get() = _chatsState

    fun getMessages() {
        viewModelScope.launch {
            CommunicationStorage.getMessages().onSuccess { messages ->
                _chatsState.value = CommunicationState.LoadingMessages
                _chatsState.value =
                    CommunicationState.Success(messages.map { convertToMessageView(it) })

            }.onFailure {
                _chatsState.value = CommunicationState.Failure(it)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private val dateFormatter = SimpleDateFormat("hh:mm")
    private suspend fun convertToMessageView(message: Message): MessageInfo {
        val textMes = message.data.Text?.text ?: TextPresentObjects.image
        val time = if (message.time != null) Date(message.time) else null
        return MessageInfo(
            message.from,
            textMes,
            time?.let { it1 -> dateFormatter.format(it1) }
        )
    }
}
