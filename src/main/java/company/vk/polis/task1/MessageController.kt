package company.vk.polis.task1


class MessageController {
    fun getValidInfo(): List<Entity> {
        return Repository.getInfo()?.filter {
            when (it) {
                is Chat -> !(it.id == null || it.userIds == null || it.messageIds == null) &&
                        !it.messageIds!!.any { it == null }
                is Message -> !(it.id == null || it.text == null || it.senderId == null ||
                        it.timestamp == null || it.state == null)
                is User -> !(it.id == null || it.name == null)
                is GroupChat -> it.messageIds != null && !it.messageIds.any { it == null }
                else -> false
            }
        } ?: emptyList()
    }

    internal fun getUserChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val info = getValidInfo()
        val chatItemList = mutableListOf<ChatItem>()

        for (chat in info) {
            when (chat) {
                !is ChatInterface -> continue
                is Chat -> if (userId != chat.userIds.senderId &&
                    userId != chat.userIds.receiverId) continue
                is GroupChat -> if (!chat.userIds.contains(userId)) continue
            }
            val lastMessage = info.find {
                it is Message && it.id == (chat as ChatInterface).messageIds?.last()
            } as Message?
            if (state == null || lastMessage?.state?.javaClass == state.javaClass) {
                val avatarUrl = if (lastMessage == null) {
                    null
                } else {
                    (info.find { it is User && it.id == lastMessage.senderId } as User?)?.avatarUrl
                }
                val whoDeleted = (info.find {
                    it is User && it.id == (lastMessage?.state as? State.DELETED)?.id
                } as User?)?.name
                chatItemList.add(ChatItem(chat.id, avatarUrl, lastMessage, whoDeleted))
            }
        }
        return chatItemList
    }

    fun getUserMessageCount(userId: Int): Int =
        getValidInfo().count { (it as? Message)?.senderId == userId }
}