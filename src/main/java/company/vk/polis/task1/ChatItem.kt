package company.vk.polis.task1

data class ChatItem(
    val chatId: Int,
    val avatarUrl: String?,
    val lastMessage: Message?,
    val deleteUserName: String? = null // for deleted messages
) {
    fun getMessageView() = when {
        lastMessage == null -> ""
        lastMessage.state is State.DELETED -> "Сообщение было удалено${
            if (deleteUserName == null) "" else " $deleteUserName"
        }"
        else -> lastMessage.text ?: ""
    }
}