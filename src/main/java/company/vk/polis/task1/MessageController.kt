package company.vk.polis.task1

class MessageController(info: List<Entity>) {
    private val validInfo = filterValidEntities(info)

    private fun filterValidEntities(entities: List<Entity>): List<Entity> {
        return entities.filter { entity ->
            when (entity)
            {
                is JavaEntity -> entity.isValid()
                else -> true
            }
        }
    }

    private fun findMessage(id: Int): Message? {
        return validInfo.filterIsInstance<Message>().find { it.id == id }
    }

    private fun findUserById(id: Int): User? {
        return validInfo.find { it is User && it.getId() == id } as? User
    }

    private fun findUserNameById(id: Int): String? {
        val user = findUserById(id)
        return user?.name
    }

    private fun getLastMessage(chat: ChatEntity) : Message?
    {
        val lastMessageId = chat.getMessageIds()?.lastOrNull()
        return if (lastMessageId != null) findMessage(lastMessageId) else null
    }

    fun getPreviewChats(userId : Int, state: MessageState? = null) : List<ChatItem> {
        val user = findUserById(userId) ?: throw NoSuchElementException("The user with the ID $userId was not found.")
        val groupChatIdsSet = user.groupChatIds.toHashSet()

        val chats = validInfo.filterIsInstance<Chat>().filter { chat -> chat.userIds.senderId == userId }
        val groupChats = validInfo.filterIsInstance<GroupChat>().filter { groupChat -> groupChatIdsSet.contains(groupChat.id) }

        return (chats + groupChats)
            .filter { chat ->
                state == null || getLastMessage(chat)?.state == state
            }
            .map { chat ->
                val lastMessage = getLastMessage(chat)

                val avatarUrl = when(chat){
                    is Chat -> findUserById(chat.userIds.receiverId)?.avatarUrl
                    is GroupChat -> chat.avatarUrl
                    else -> error("Unexpected type: ${chat.javaClass.simpleName}")
                }

                ChatItem(id = chat.id, avatarUrl = avatarUrl, lastMessage = lastMessage, lastMessageState = lastMessage?.state, getUserName = ::findUserNameById)
            }
    }

    fun countMessagesByUserId(userId: Int): Int {
        return validInfo.filterIsInstance<Message>().count { it.senderId == userId }
    }
}