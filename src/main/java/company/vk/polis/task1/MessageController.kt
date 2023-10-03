package company.vk.polis.task1

class MessageController {
    val validInfo: List<Entity> = filterInfo()
    private val chatList: List<Chat> = getChatList(validInfo)
    private val groupChatList: List<GroupChat> = getGroupChatList(validInfo)
    private val messageList: List<Message> = getMessageList(validInfo)
    private val messageIdMap: Map<Int, Message> = getMessageIdMap(validInfo)
    private val userToUserIdMap: Map<Int, User> = getUserToUserId(validInfo)


    private fun filterInfo(): List<Entity> {
        return Repository.getInfo().filterNotNull().filter { entity ->
            entity.id != null &&
                    when (entity) {
                        is Chat -> !(entity.messageIds == null || entity.userIds == null)
                        is Message -> !(entity.senderId == null
                                || entity.text == null || entity.timestamp == null || entity.state == null)
                        is User -> entity.name != null
                        is GroupChat -> true
                        else -> false
                    }
        }


    }

    private fun getMessageIdMap(validInfo: List<Entity>): Map<Int, Message> {
        return validInfo.filterIsInstance<Message>().associateBy({ it.id() }, { it })
    }

    private fun getUserToUserId(validInfo: List<Entity>): Map<Int, User> {
        return validInfo.filterIsInstance<User>().associateBy({ it.id() }, { it })
    }

    private fun getChatList(validInfo: List<Entity>): List<Chat> {
        return validInfo.filterIsInstance<Chat>()
    }

    private fun getGroupChatList(validInfo: List<Entity>): List<GroupChat> {
        return validInfo.filterIsInstance<GroupChat>()
    }

    private fun getMessageList(validInfo: List<Entity>): List<Message> {
        return validInfo.filterIsInstance<Message>()
    }


    data class ChatItem(
        val avatarURL: String?,
        var lastMessage: String,
        val messageState: State
    )


    fun getChatItems(userId: Int, messageState: State? = null): List<ChatItem> {
        val chatItemList: MutableList<ChatItem> = mutableListOf()

        val receiverChats: List<Chat> = chatList.filter { chat -> chat.userIds.receiverId == userId }
        for (chat in receiverChats) {
            val messages = messageIdMap.filterKeys { chat.messageIds.contains(it) }.values.toList()
            val lastMessage = messages.maxBy { it.timestamp }
            val chatItem = ChatItem(userToUserIdMap[chat.userIds.senderId]?.avatarUrl,
                getChatItemMessage(lastMessage),
                lastMessage.state)
            chatItemList.add(chatItem)
        }

        val receiverGroupChats: List<GroupChat> = groupChatList.filter { chat -> chat.userIds.contains(userId) }
        for (groupChat in receiverGroupChats) {
            val messages = messageIdMap.filterKeys { groupChat.messageIds.contains(it) }.values.toList()
            val lastMessage = messages.maxBy { it.timestamp }
            val chatItem = ChatItem(groupChat.avatarUrl, getChatItemMessage(lastMessage), lastMessage.state)
            chatItemList.add(chatItem)
        }

        return if (messageState != null) {
            chatItemList.filter { chatItem -> chatItem.messageState == messageState }
        } else {
            chatItemList
        }

    }

    fun getCountOfMessage(userId: Int): Int {
        return messageList.count { message -> message.senderId == userId }
    }


    private fun getChatItemMessage(message: Message): String {
        return if (message.state is State.Deleted)
            "Сообщение было удалено ${userToUserIdMap[(message.state as State.Deleted).idDeleted]}"
             else message.text
    }
}



