package company.vk.polis.task1


class MessageController {
    private fun checkValidChat(it: Chat) = !(it.id == null || it.userIds == null ||
            it.messageIds == null) && !it.messageIds!!.any { it == null }
    private fun checkValidMessage(it: Message) = !(it.id == null || it.text == null ||
            it.senderId == null || it.timestamp == null || it.state == null)
    private fun checkValidUser(it: User) = !(it.id == null || it.name == null)
    private fun checkValidGroupChat(it: GroupChat) = it.messageIds != null && !it.messageIds.any { it == null }



    fun getValidInfo(): List<Entity> {
        return Repository.getInfo()?.filter {
            when (it) {
                is Chat -> checkValidChat(it)
                is Message -> checkValidMessage(it)
                is User -> checkValidUser(it)
                is GroupChat -> checkValidGroupChat(it)
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

            val lastMessage = info.filter {
                it is Message && it.id in ((chat as ChatInterface).messageIds ?: emptyList())
            }.maxByOrNull { (it as Message).timestamp } as Message?

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