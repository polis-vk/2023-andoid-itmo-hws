package company.vk.polis.task1

object MessageController {
    fun getValidEntities(): List<Entity> {
        return Repository.getInfo().filter { entity ->
            isValid(entity)
        }
    }

    fun getNumberMessages(usedId: Int): Int {
        return getValidEntities().filter { it is Message && it.senderId == usedId }.size
    }

    internal fun getChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val entities = getValidEntities()
        val chatItems = mutableListOf<ChatItem>()

        for (chat in entities.filterIsInstance<BaseChat>().filter {
            when (it) {
                is Chat -> it.userIds.senderId == userId
                is GroupChat -> it.containsUser(userId)
                else -> false
            }
        }) {
            val messages = mutableListOf<Message>()
            chat.getMessageIds().forEach { messId ->
                messages.add(entities.filterIsInstance<Message>().find { it.id == messId } as Message)
            }
            val lastMessage = messages.maxBy { it.timestamp }
            if (state == null || lastMessage.state == state) {
                when (chat) {
                    is Chat -> {
                        val user = entities.filterIsInstance<User>().find { it.id == chat.userIds.receiverId } as User
                        chatItems.add(ChatItem(lastMessage, user.name, user.avatarUrl))
                    }
                    is GroupChat -> (
                            chatItems.add(
                                ChatItem(
                                    lastMessage,
                                    chat.name,
                                    chat.avatarUrl,
                                    chat.getNumberUsers()
                                )
                            ))
                    else -> {}
                }

            }
        }
        return chatItems
    }

    private fun isValid(entity: Entity) = when (entity) {
        is User -> isNotNull(entity.id, entity.name)
        is Message -> isNotNull(entity.id, entity.state, entity.senderId, entity.text, entity.timestamp)
        is Chat -> isNotNull(entity.id, entity.userIds, entity.messageIds)
        is GroupChat -> isNotNull(entity.id, entity.userIds, entity.messageIds, entity.name)
        else -> false
    }

    private fun isNotNull(vararg props: Any?): Boolean {
        return props.all { it != null }
    }
}
