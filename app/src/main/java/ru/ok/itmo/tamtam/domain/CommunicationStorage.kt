package ru.ok.itmo.tamtam.domain

import ru.ok.itmo.tamtam.client.provider.ChatsProvider
import ru.ok.itmo.tamtam.dto.ChannelName
import ru.ok.itmo.tamtam.dto.Message

object CommunicationStorage {
    private val chatsProvider = ChatsProvider()

    var channelName: ChannelName? = null

    suspend fun getMessages(): Result<List<Message>> {
        return if(channelName != null) {
            chatsProvider.getChannelMessages(channelName!!)
        }else {
            Result.failure(ErrorType.IncorrectCommunication())
        }
    }
}
