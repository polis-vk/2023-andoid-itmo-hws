package company.vk.polis.task1

internal class MessageController {

    fun chatItems(userId: Int, state: State? = null): List<ChatItem> {
        val user = userById(userId)
        val chats = validChats(userId)
        val messagesById: MutableMap<Int, Message> = HashMap()
        validMessages(userId).forEach {
            messagesById[it.id] = it
        }

        val result: MutableList<ChatItem> = ArrayList()
        for (chat in chats) {
            val lastMessageId = chat.messageIds?.last()
            val lastMessage = messagesById[lastMessageId]
            if (lastMessage != null
                    && lastMessage.senderId == userId
                    && (state == null || lastMessage.state == state)) {
                val chatItem = ChatItem(user?.avatarUrl, lastMessage, lastMessage.state)
                result.add(chatItem)
            }
        }
        return result
    }

    fun numberOfMessages(userId: Int): Int {
        return validMessages(userId).size
    }

    fun userById(userId: Int): User? {
        return validUsers().firstOrNull {
            it.id.equals(userId)
        }
    }

    private fun validUsers(): List<User> {
        val users: MutableList<User> = ArrayList()
        for (entity in Repository.getInfo()) {
            if (entity is User) {
                if (entity.isValid()) {
                    users.add(entity)
                }
            }
        }
        return users
    }

    private fun validMessages(bySenderUserId: Int?): List<Message> {
        val messages: MutableList<Message> = ArrayList()
        for (entity in Repository.getInfo()) {
            if (entity is Message) {
                if (entity.isValid()
                        && (bySenderUserId == null
                                || entity.senderId == bySenderUserId)) {
                    messages.add(entity)
                }
            }
        }
        return messages
    }

    private fun validChats(bySenderUserId: Int? = null): List<ChatInterface> {
        val chats: MutableList<ChatInterface> = ArrayList()
        for (entity in Repository.getInfo()) {
            when (entity) {
                is Chat ->
                    if (entity.isValid()
                            && (bySenderUserId == null
                                    || entity.userIds.senderId == bySenderUserId)) {
                        chats.add(entity)

                    }

                is GroupChat ->
                    if (entity.isValid()
                            && (bySenderUserId == null
                                    || entity.userIds.contains(bySenderUserId))) {
                        chats.add(entity)
                    }
            }
        }
        return chats
    }

    private fun Message.isValid(): Boolean {
        return id != null
                && senderId != null
                && state != null
                && text != null
                && timestamp != null
    }

    private fun User.isValid(): Boolean {
        return id != null
                && name != null
    }

    private fun Chat.isValid(): Boolean {
        return id != null
                && userIds != null
                && messageIds != null
    }

    private fun GroupChat.isValid(): Boolean {
        return messageIds == null
    }

}
