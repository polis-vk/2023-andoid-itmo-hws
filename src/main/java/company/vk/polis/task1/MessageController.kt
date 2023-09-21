package company.vk.polis.task1

import org.jetbrains.annotations.Nullable

class MessageController(info: List<Entity>) {
    private val validInfo = filterValidEntities(info)

    private fun filterValidEntities(entities: List<Entity>): List<Entity> {
        return entities.filter { entity ->
            val fields = entity.javaClass.declaredFields
            fields.all { field ->
                val isNullable = field.getAnnotation(Nullable::class.java) != null
                val value = field.get(entity)
                value != null || isNullable
            }
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

        return chats
            .filter { chat ->
                chat.userIds.senderId == userId || chat.userIds.receiverId == userId
            }
            .filter { chat ->
                val lastMessageId = chat.messageIds.lastOrNull()
                val lastMessage = if (lastMessageId != null) findMessage(lastMessageId) else null
                state == null || lastMessage?.state == state
            }
            .map { chat ->
                val lastMessageId = chat.messageIds.lastOrNull()
                val lastMessage = if (lastMessageId != null) findMessage(lastMessageId) else null
                val avatarUrl = findUserById(if (chat.userIds.senderId != userId) chat.userIds.senderId else chat.userIds.receiverId )?.avatarUrl
                ChatItem(avatarUrl = avatarUrl, lastMessage = lastMessage, ::findUserNameById)
            }
    }
}