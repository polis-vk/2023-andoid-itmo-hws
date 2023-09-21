package company.vk.polis.task1

import org.jetbrains.annotations.Nullable

class MessageController(info: List<Entity>) {
    private val validInfo = filterValidEntities(info)

    private fun filterValidEntities(entities: List<Entity>): List<Entity> {
        return entities.filter { entity ->
            entity.isValid()
        }
    }

    private fun findMessage(id: Int): Message? {
        return validInfo.filterIsInstance<Message>().find { it.id == id }
    }

    private fun findUserById(id: Int): User? {
        return validInfo.find { it is User && it.getId() == id } as? User
    }

    fun findUserNameById(id: Int): String? {
        val user = findUserById(id)
        return user?.name
    }

    fun getPreviewChats(userId : Int, state: MessageState? = null) : List<ChatItem> {
        val chats = validInfo.filterIsInstance<Chat>()
        val groupChats = validInfo.filterIsInstance<GroupChat>()

        return (chats + groupChats)
            .filter { chat ->
                when (chat){
                    is Chat -> chat.userIds.senderId == userId || chat.userIds.receiverId == userId
                    is GroupChat -> chat.userIds.any { it.id == userId }
                    else -> false
                }
            }
            .filter { chat ->
                val lastMessageId = when (chat) {
                    is Chat -> chat.messageIds.lastOrNull()
                    is GroupChat -> chat.messageIds.lastOrNull()
                    else -> null
                }
                val lastMessage = if (lastMessageId != null) findMessage(lastMessageId) else null
                state == null || lastMessage?.state == state
            }
            .map { chat ->
                val lastMessageId = when (chat) {
                    is Chat -> chat.messageIds.lastOrNull()
                    is GroupChat -> chat.messageIds.lastOrNull()
                    else -> null
                }

                val lastMessage = if (lastMessageId != null) findMessage(lastMessageId) else null

                val avatarUrl = when(chat){
                    is Chat -> findUserById(if (chat.userIds.senderId != userId) chat.userIds.senderId else chat.userIds.receiverId )?.avatarUrl
                    is GroupChat -> chat.avatarUrl
                    else -> null
                }

                ChatItem(avatarUrl = avatarUrl, lastMessage = lastMessage, ::findUserNameById)
            }
    }

    fun countMessagesByUserId(userId: Int): Int {
        return validInfo.filterIsInstance<Message>().count { it.senderId == userId }
    }
}