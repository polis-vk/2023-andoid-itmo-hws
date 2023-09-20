package company.vk.polis.task1

class MessageController {

    private val entities: List<Entity> by lazy { Repository.getInfo() }

    val validData: List<Entity> by lazy {
        entities.filter { entity: Entity -> when(entity) {
            is User -> isUserValid(entity)
            is Chat -> isChatValid(entity)
            is Message -> isMessageValid(entity)
            is GroupChat -> true
            else -> false
        } }
    }

    fun getUserNameById(userId: Int): String? {
        return validData.filterIsInstance<User>().firstOrNull { it.id == userId }?.name
    }

    fun getUserChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val messages = validData.filterIsInstance<Message>()
        val user = validData.filterIsInstance<User>().find { it.id == userId } ?: return emptyList()
        val chats = validData.filter {
            it is Chat && (it.userIds.senderId == userId || it.userIds.receiverId == userId) ||
                    it is GroupChat && it.memberIds.contains(userId)
        }
        val items = mutableListOf<ChatItem>()
        for(chat in chats) {
            val (messageIds, avatarUrl) = when(chat) {
                is Chat -> chat.messageIds to user.avatarUrl
                is GroupChat -> chat.messageIds to chat.avatarUrl
                else -> emptyList<Int>() to null
            }
            messageIds.mapNotNull { messageId -> messages.firstOrNull { it.id == messageId } }
                .filter { it.senderId == userId && (state == null || it.state == state) }
                .maxByOrNull { it.timestamp }?.let {
                    items.add(it.toChatItem(avatarUrl))
                }
        }
        return items
    }

    fun getUserMessagesCount(userId: Int) = validData.count { it is Message && it.senderId == userId }

    private fun isUserValid(user: User): Boolean {
        return user.id != null && user.name != null
    }

    private fun isChatValid(chat: Chat): Boolean {
        return chat.id != null && chat.userIds != null && chat.messageIds != null
    }

    private fun isMessageValid(message: Message): Boolean {
        return message.id != null && message.text != null && message.senderId != null && message.timestamp != null && message.state != null
    }

    private fun Message?.toChatItem(avatarUrl: String?) = ChatItem(
        avatarUrl = avatarUrl,
        lastMessage = this?.text?.let { handleLastMessageByState(it, state) } ?: "Нет сообщений",
        state = this?.state ?: State.READ()
    )

    private fun handleLastMessageByState(text: String, state: State?) = if (state is State.DELETE) {
        "Сообщение было удалено ${getUserNameById(state.id)}"
    } else {
        text
    }

}