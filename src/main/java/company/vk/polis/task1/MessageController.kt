package company.vk.polis.task1

class MessageController {
    private val repInfo: List<Entity> = Repository.getInfo()
    private val messageByUserId = mutableMapOf<Int, MutableList<Message>>()
    private val userByUserId = mutableMapOf<Int, User>()
    private val chats = mutableListOf<Chat>()
    private val groupChats = mutableListOf<GroupChat>()
    val parsedInfo = parseRepInfo()

    private fun parseRepInfo(): List<Entity> {
        val result = mutableListOf<Entity>()
        repInfo.forEach { entity ->
            when (entity) {
                is User -> {
                    if (entity.id != null && entity.name != null) {
                        result.add(entity)
                        userByUserId[entity.id] = entity
                    }
                }

                is Message -> {
                    if (entity.id != null && entity.senderId != null && entity.state != null && entity.text != null && entity.timestamp != null) {
                        result.add(entity)
                        if (messageByUserId.containsKey(entity.senderId)) {
                            messageByUserId[entity.senderId]?.add(entity)
                        } else {
                            messageByUserId[entity.senderId] = mutableListOf()
                            messageByUserId[entity.senderId]?.add(entity)
                        }
                    }
                }

                is Chat -> {
                    if (entity.id != null && entity.messageIds != null && entity.userIds != null) {
                        result.add(entity)
                        chats.add(entity)
                    }

                }

                is GroupChat -> {
                    result.add(entity)
                    groupChats.add(entity)
                }

                else -> throw IllegalArgumentException("Unknown Entity")
            }
        }
        return result
    }

    fun getUserChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val result = mutableListOf<ChatItem>()
        chats.forEach { chat ->
            if (chat.userIds.senderId == userId || chat.userIds.receiverId == userId) {
                createChatItem(userId, chat.messageIds, state, groupChat = false)?.let { result.add(it) }
            }
        }

        groupChats.forEach { chat ->
            chat.userIds.forEach { user ->
                if (user == userId) {
                    createChatItem(userId, chat.messageIds, state, groupChat = true)?.let { result.add(it) }
                }
            }
        }

        return result
    }

    private fun createChatItem(
        userId: Int,
        messageIds: List<Int>,
        state: State?,
        groupChat: Boolean,
    ): ChatItem? {
        val userMessagesInChat = mutableListOf<Message>()

        messageByUserId[userId]?.forEach { msg ->
            if (messageIds.contains(msg.id))
                userMessagesInChat.add(msg)
        }
        if (userMessagesInChat.isNotEmpty()) {
            val resMsg = userMessagesInChat.maxBy { it.timestamp }
            if (state != null && resMsg.state.equals(state) || state == null) {
                return if (resMsg.state is State.DELETED) {
                    ChatItem(
                        userByUserId[userId]?.avatarUrl,
                        "Сообщение было удалено " + userByUserId[(resMsg.state as State.DELETED).id]?.name,
                        resMsg.state,
                        groupChat
                    )
                } else {
                    ChatItem(userByUserId[userId]?.avatarUrl, resMsg.text, resMsg.state, groupChat)
                }
            }

        }
        return null
    }

    internal fun sendedMessagesCount(userId: Int): Int? {
        return messageByUserId[userId]?.size
    }

}
