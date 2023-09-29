package company.vk.polis.task1


data class ChatItem(
    val avatarUrl: String?,
    val lastMessage: Message,
    val lastTime: Long,
    val chatid : Int,
    val state: State
)