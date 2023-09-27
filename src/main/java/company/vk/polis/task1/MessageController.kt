package company.vk.polis.task1

object MessageController {
    private fun filterNotNull(entities: List<Entity>) = entities.filterNot { e ->
        when (e) {
            is User -> listOf(e.id, e.name)
            is Message -> listOf(e.id, e.text, e.senderId, e.timestamp, e.state)
            is Chat -> listOf(e.id, e.userIds, e.messageIds)
            is GroupChat -> listOf()
            else -> throw IllegalArgumentException("Unsupported entity type found: $e")
        }.any { it == null }
    }

    fun valid() = filterNotNull(Repository.getInfo())

    private inline fun <reified E : Entity> find(id: Int) = valid().filterIsInstance<E>().find { it.id == id }

    fun findMessage(userId: Int, state: State?): List<ChatItem> = valid().mapNotNull {
        when (it) {
            is Conversation -> find<Message>(it.messageIds().last())?.let { message ->
                when {
                    state == null || message.state == state -> {
                        find<User>(message.senderId)?.let { user ->
                            if (state is DELETED) {
                                println("Сообщение было удалено пользователем ${find<User>(state.userId)?.name}")
                            }
                            ChatItem(user.avatarUrl, message.id, message.state)
                        }
                    }
                    else -> null
                }
            }
            else -> null
        }
    }

    fun countMessages(userId: Int) = valid().filterIsInstance<Message>().count { it.senderId == userId }
}
