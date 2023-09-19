package company.vk.polis.task1

class MessageController {
    private val entities: List<Entity> = Repository.getInfo().filter { EntityValidator.validateEntity(it) }
    private val messages = entities.filterIsInstance<Message>()
    private val messageById: HashMap<Int, Message> = messages.associateBy { it.id } as HashMap<Int, Message>
    private val chats = entities.filterIsInstance<MetaChat>()
    private val userById: HashMap<Int, User> =
        entities.filterIsInstance<User>().associateBy { it.id } as HashMap<Int, User>

    fun getChatsView(userId: Int, filterByState: MessageState? = null): List<ChatItem> =
        chats.filter { it.getUserIds()!!.contains(userId) }
            .map { chat -> chat to chat.getMessageIds()!!.map { messageById[it]!! }.maxBy { it.timestamp } }
            .filter { null == filterByState || it.second.state.getState() == filterByState.getState() }
            .map { toChatItem(userId, it.first, it.second) }

    fun getMessagesCount(userId: Int): Int = messages.count { it.senderId == userId }

    private fun getChatAvatar(viewerId: Int, chat: MetaChat): String? = when (chat) {
        is GroupChat -> chat.avatarUrl
        is Chat -> if (viewerId == chat.userIds.senderId) userById[chat.userIds.receiverId]?.avatarUrl else userById[chat.userIds.senderId]?.avatarUrl
        else -> throw NotImplementedError("Chat of type ${chat.javaClass} is not implemented")
    }

    private fun toChatItem(viewerId: Int, chat: MetaChat, lastMessage: Message): ChatItem {
        val messageStateInfo = when (lastMessage.state.getState()) {
            MessageStateEnum.READ -> "read"
            MessageStateEnum.UNREAD -> "unread"
            MessageStateEnum.DELETED -> "deleted by ${userById[lastMessage.state.getDeletedBy()]?.name}"
        }
        return ChatItem(
            getChatAvatar(viewerId, chat),
            lastMessage.text,
            userById[lastMessage.senderId]?.name,
            messageStateInfo
        )
    }
}