package ru.ok.itmo.example.chatsPackage

import retrofit2.Call


class ChatUseCase(private val api: chatApi) {

    fun myGetAllChannels(): Call<List<String>> {
        try {
            return api.getAllChannels()
        } catch (e: Exception) {
            throw e
        }
    }


    fun myGetChannelMessages(channelId: String): Call<List<Message>> {
        return try {
            api.getChannelMessages(channelId)
        } catch (e: Exception) {
            throw e
        }
    }

}
