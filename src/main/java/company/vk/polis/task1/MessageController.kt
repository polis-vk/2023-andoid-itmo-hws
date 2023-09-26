package company.vk.polis.task1


class MessageController {
    private fun isValidChat(it: Chat) = !(it.id == null || it.userIds == null ||
            it.messageIds == null) && !it.messageIds!!.any { it == null }
    private fun isValidMessage(it: Message) = !(it.id == null || it.text == null ||
            it.senderId == null || it.timestamp == null || it.state == null)
    private fun isValidUser(it: User) = !(it.id == null || it.name == null)
    private fun isValidGroupChat(it: GroupChat) = it.messageIds != null && !it.messageIds.any { it == null }

    fun getValidInfo(): List<Entity> {
        return Repository.getInfo()?.filter {
            when (it) {
                is Chat -> isValidChat(it)
                is Message -> isValidMessage(it)
                is User -> isValidUser(it)
                is GroupChat -> isValidGroupChat(it)
                else -> false
            }
        } ?: emptyList()
    }

    private fun isUserInChat(userId: Int, chat: ChatInterface) = when (chat) {
        is Chat -> userId == chat.userIds.senderId || userId != chat.userIds.receiverId
        is GroupChat -> chat.userIds.contains(userId)
        // Only Chat and Group should inherit ChatInterface
        else -> throw IllegalArgumentException("Unsupported descendant of ChatInterface")
    }

    private fun getLastMessage(chat: ChatInterface, info: List<Entity>): Message? {
        return info.filter {
            it is Message && it.id in (chat.messageIds ?: emptyList())
        }.maxByOrNull { (it as Message).timestamp } as Message?
    }

    private fun checkMessageState(message: Message?, state: State?): Boolean {
        return state == null || message?.state?.javaClass == state.javaClass
    }

    private fun getAvatarUrl(userId: Int?, info: List<Entity>) = when(userId) {
        null -> null
        else -> (info.find { it is User && it.id == userId } as User?)?.avatarUrl
    }

    private fun whoDeleted(message: Message?, info: List<Entity>) = when {
        message == null || message.state !is State.DELETED -> null
        else -> (info.find {
            it is User && it.id == (message.state as State.DELETED).id
        } as User?)?.name
    }

    internal fun getUserChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val info = getValidInfo()

        return info.asSequence()
            .filterIsInstance<ChatInterface>()
            .filter { isUserInChat(userId, it) }
            .map { it to getLastMessage(it, info) } // (chat, lastMessage)
            .filter { checkMessageState(it.second, state) }
            .map { ChatItem(
                it.first.id,
                getAvatarUrl(it.second?.senderId, info),
                it.second,
                whoDeleted(it.second, info)
            ) }
            .toList()
    }

    fun getUserMessageCount(userId: Int): Int =
        getValidInfo().count { (it as? Message)?.senderId == userId }
}