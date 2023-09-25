package company.vk.polis.task1

class MessageController {
    private val entities = Repository.getInfo().filter { isValid(it) }

    private fun isValid(entity: Entity) : Boolean {
        return when(entity) {
            is Chat -> entity.id != null && entity.userIds != null && entity.messageIds != null
            is Message -> entity.id != null && entity.text != null && entity.senderId != null && entity.timestamp != null && entity.state != null
            is User -> entity.id != null && entity.name != null
            else -> false
        }
    }

    fun getChatItem(user_id: Int?, state: StateMsg?) : ArrayList<ChatItem> {
        val chats = entities.filterIsInstance<Chat>().filter { it.userIds.senderId == user_id || it.userIds.receiverId == user_id }
        val userChatItem = ArrayList<ChatItem>()
        for(chat in chats) {
            val chatMessagesIds = chat.messageIds;
            val messages = entities.filterIsInstance<Message>().filter { it.id in chatMessagesIds && it.state == state }
            val listChatMsg = messages.sortedBy { it -> it.timestamp }
            val lastMsg : String? = listChatMsg[listChatMsg.size - 1].text
            val listUserUrl = entities.filterIsInstance<User>().filter { it.id == user_id }
            val userUrl : String? = listUserUrl[0].avatarUrl
            userChatItem.add(ChatItem(userUrl, lastMsg, state!!, user_id))
        }
        return userChatItem
    }
    fun countSendedMessages(userId: Int?) :Int {
        val chats = entities.filterIsInstance<Chat>().filter { it.userIds.receiverId == userId }
        return chats.size;
    }
}
