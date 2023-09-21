package company.vk.polis.task1.ui

import company.vk.polis.task1.models.MessageState

data class ChatItem(val avatarUrl: String?, val lastMessage: String, val state: MessageState)
