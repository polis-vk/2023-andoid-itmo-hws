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
                    if(entity.id != null && entity.users != null){
                        result.add(entity)
                        groupChats.add(entity)
                    }
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
                val userMessagesInChat = mutableListOf<Message>()
                messageByUserId[userId]?.forEach { msg ->
                    if(chat.messageIds.contains(msg.id))
                        userMessagesInChat.add(msg)
                }
                if(userMessagesInChat.isNotEmpty()){
                    val resMsg = userMessagesInChat.maxBy { it.timestamp }
                    if (state.isNotEmpty() && resMsg.state == state[0] || state.isEmpty())
                        result.add(ChatItem(userByUserId[userId]?.avatarUrl, resMsg.text, resMsg.state))
                }
            }
        }

        return result
    }

    fun getParsedInfo(): List<Entity> {
        return parsedInfo
    }

    internal fun sendedMessagesCount(userId: Int): Int? {
        return messageByUserId[userId]?.size
    }

}