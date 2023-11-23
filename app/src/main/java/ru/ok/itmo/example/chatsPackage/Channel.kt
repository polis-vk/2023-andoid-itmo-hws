package ru.ok.itmo.example.chatsPackage

import java.util.Date

data class Channel(
    val name: String,
    val lastMessageText :String
)

data class MessageDataText(
    val text: String
)

data class MessageDataImage(
    val link: String
)

data class MessageData(
    val Text: MessageDataText? = null,
    val Image: MessageDataImage? = null,
)


data class Message(
    val id: Long,
    val from: String,
    val to: String,
    val data: MessageData,
    private val time: Long,
)