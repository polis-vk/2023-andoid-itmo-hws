package company.vk.polis.task1.models

import org.jetbrains.annotations.Nullable

data class ChatItem(val lastMessage: Message, val state: State, @Nullable val uri: String?) {
}