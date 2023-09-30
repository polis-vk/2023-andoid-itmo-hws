package company.vk.polis.task1

object MessageController {
    private fun getValidEntities(): List<Entity> {
        return Repository.getInfo().filter { it != null && isValid(it) }
    }

    fun getNumberMessages(usedId: Int): Int {
        return getValidEntities().filter { it is Message && it.senderId == usedId }.size
    }

    internal fun getChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val entities = getValidEntities()
        return entities.filterIsInstance<BaseChat>()
            .filter { isUserInChat(userId, it) }
            .mapNotNull { getChatItem(it, entities, getLastMessage(it, entities), state) }
    }

    private fun isValid(entity: Entity) = when (entity) {
        is User -> isNotNull(entity.id, entity.name)
        is Message -> isNotNull(entity.id, entity.state, entity.senderId, entity.text, entity.timestamp)
        is Chat -> isNotNull(entity.id, entity.userIds, entity.messageIds)
        is GroupChat -> isNotNull(entity.id, entity.userIds, entity.messageIds, entity.name)
        else -> true
    }

    private fun isNotNull(vararg props: Any?): Boolean {
        return props.all { it != null }
    }

    private fun isUserInChat(userId: Int, chat: Entity) = when (chat) {
        is Chat -> chat.userIds.senderId == userId
        is GroupChat -> chat.containsUser(userId)
        else -> false
    }

    private fun getChatItem(chat: BaseChat, entities: List<Entity>, lastMessage: Message?, state: State?): ChatItem? {
        return lastMessage?.takeIf { state == null || it.state::class == state::class }?.let { message ->
            when (chat) {
                is Chat -> entities.filterIsInstance<User>().find { it.id == chat.userIds.receiverId }
                    ?.let { ChatItem(genTextMessageToChatItem(message), it.name, message.state, it.avatarUrl) }
                is GroupChat -> ChatItem(genTextMessageToChatItem(message), chat.name, message.state, chat.avatarUrl)
                else -> null
            }
        }
    }

    private fun getLastMessage(chat: BaseChat, entities: List<Entity>): Message? {
        return entities.filterIsInstance<Message>()
            .filter { it.id in chat.messageIds }
            .maxByOrNull { it.timestamp }
    }

    private fun genTextMessageToChatItem(message: Message) = when (message.state) {
        is State.Deleted -> "The message was deleted by user ${(message.state as State.Deleted).userId}"
        else -> message.text
    }
}
