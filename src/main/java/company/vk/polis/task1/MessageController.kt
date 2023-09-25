package company.vk.polis.task1

class MessageController {
    private val repInfo: List<Entity> = Repository.getInfo()
    private var parsedInfo = listOf<Entity>()
    private val messageByUserId = mutableMapOf<Int, MutableList<Message>>() //По юзеру все его сообщения
    private val userByUserId = mutableMapOf<Int, User>()
    private val chats = mutableListOf<Chat>()
    private val groupChats = mutableListOf<GroupChat>()


    init {
        parsedInfo = parseRepInfo()
    }

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

    fun getUserChatItems(userId: Int, vararg state: State): List<ChatItem> {
        val result = mutableListOf<ChatItem>()
        chats.forEach { chat ->
            if (chat.userIds.senderId == userId || chat.userIds.receiverId == userId) {
                createChatItem(userId, chat.messageIds, state, false)?.let { result.add(it) }
            }
        }

        groupChats.forEach { chat ->
            chat.userIds.forEach { user ->
                if (user == userId) {
                    createChatItem(userId, chat.messageIds, state, true)?.let { result.add(it) }
                }
            }
        }

        return result
    }

    private fun createChatItem(
        userId: Int,
        messageIds: List<Int>,
        state: Array<out State>,
        groupChat: Boolean,
    ): ChatItem? {
        val userMessagesInChat = mutableListOf<Message>()

        messageByUserId[userId]?.forEach { msg ->
            if (messageIds.contains(msg.id))
                userMessagesInChat.add(msg)
        }
        if (userMessagesInChat.isNotEmpty()) {
            val resMsg = userMessagesInChat.maxBy { it.timestamp }
            if (state.isNotEmpty() && resMsg.state.equals(state[0]) || state.isEmpty()) {
                return if (resMsg.state is State.Deleted) {
                    ChatItem(
                        userByUserId[userId]?.avatarUrl,
                        resMsg.text,
                        resMsg.state,
                        groupChat,
                        userByUserId[(resMsg.state as State.Deleted).id]?.name
                    )
                } else {
                    ChatItem(userByUserId[userId]?.avatarUrl, resMsg.text, resMsg.state, groupChat, null)
                }
            }

        }
        return null
    }

    fun getParsedInfo(): List<Entity> {
        return parsedInfo
    }

    internal fun sendedMessagesCount(userId: Int): Int? {
        return messageByUserId[userId]?.size
    }

}
