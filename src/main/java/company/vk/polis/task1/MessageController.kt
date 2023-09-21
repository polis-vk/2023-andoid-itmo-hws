package company.vk.polis.task1

class MessageController {
    private fun validateEntity(entity: Entity): Boolean {
        return when (entity) {
            is Chat -> entity.id != null && entity.messageIds != null && entity.userIds != null && entity.userIds.receiverId != null && entity.userIds.senderId != null
            is Message -> entity.id != null && entity.text != null && entity.senderId != null && entity.timestamp != null && entity.state != null
            is User -> entity.id != null && entity.name != null
            is GroupChat -> entity.id != null && entity.messageIdList != null && entity.receiverIds != null && entity.userId != null
            else -> false
        }
    }

    private val entities = Repository.getInfo().filter{entity -> validateEntity(entity)}

    private val userById: Map<Int, User> = entities.filterIsInstance<User>().associateBy { it.id }

    private val messageById: Map<Int, Message> = entities.filterIsInstance<Message>().associateBy { it.id }
    data class ChatItem(val avatarUrl: String?, val lastMessage: String, val state: String)

    private fun getChats(userId: Int): List<ChatInterface> {
        return entities.filterIsInstance<ChatInterface>().filter{chat -> chat.getSenderId() == userId}

    }
    fun getChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val chats = getChats(userId)
        val result: MutableList<ChatItem> = ArrayList()
        for (chat in chats) {
            val avatarUrl = when (chat) {
                is Chat -> userById[chat.userIds.receiverId]!!.avatarUrl
                is GroupChat -> chat.avatarUrl
                else -> null
            }
            val lastMessage = chat.getMessageIds().map { messageId -> messageById[messageId] }.filter { message -> state == null || message!!.state == state}.maxByOrNull { it!!.timestamp }
                    ?: continue
            val stateText = when (val messageState = lastMessage.state) {
                is State.READ -> "READ"
                is State.UNREAD -> "UNREAD"
                is State.DELETED -> "Сообщение было удалено ${userById[messageState.userId]}"
            }
            result.add(ChatItem(avatarUrl, lastMessage.text, stateText))
        }
        return result
    }

    fun countMessages(userId: Int): Int {
        val chats = entities.filterIsInstance<Chat>().filter{chat -> chat.userIds.senderId == userId}
        return chats.sumOf { it.messageIds.size }
    }

}