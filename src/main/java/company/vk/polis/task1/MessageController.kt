package company.vk.polis.task1

class MessageController {

    private inner class CommonChat constructor(
        val id: Int,
        private val _chat: Chat? = null,
        private val _groupChat: GroupChat? = null
    ) {
        constructor(chat: Chat) : this(chat.id, _chat = chat)
        constructor(groupChat: GroupChat) : this(groupChat.id, _groupChat = groupChat)

        private fun getMessageSequence(messagesIds: List<Int?>): Sequence<Message> =
            messagesIds.asSequence()
                .filterNotNull()
                .map { messages[it] }
                .filterNotNull()

        fun messagesFromUser(userId: Int): Sequence<Message> = if (contains(userId)) {
            getMessageSequence(_chat?.messageIds ?: _groupChat?.messageIds ?: listOf())
                .filter { it.senderId == userId }
        } else sequenceOf()

        fun messagesToUser(userId: Int): Sequence<Message> = if (contains(userId)) {
            getMessageSequence(_chat?.messageIds ?: _groupChat?.messageIds ?: listOf())
                .filter { it.senderId != userId }
        } else sequenceOf()


        operator fun contains(userId: Int) = if (_chat != null) {
            _chat.userIds.receiverId == userId || _chat.userIds.senderId == userId
        } else if (_groupChat != null) {
            userId in _groupChat.userIds
        } else false
    }


    private val messages: Map<Int, Message>
    private val users: Map<Int, User>
    private val chats: Map<Int, CommonChat>

    init {
        val messages = mutableListOf<Message>()
        val users = mutableListOf<User>()
        val chats = mutableListOf<CommonChat>()

        Repository.getInfo().forEach {
            when (it) {
                is Message -> {
                    if (it.isValid()) messages += it
                }

                is Chat -> {
                    if (it.isValid()) chats += CommonChat(it)
                }

                is User -> {
                    if (it.isValid()) users += it
                }

                is GroupChat -> chats += CommonChat(it)
            }
        }

        this.messages = messages.associateBy { it.id }
        this.users = users.associateBy { it.id }
        this.chats = chats.associateBy { it.id }
    }

    fun getMessagesToUser(userId: Int, messageState: MessageState? = null): List<ChatItem> =
        chats.values.asSequence()
            .mapNotNull {
                it.messagesToUser(userId)
                    .filter { message -> messageState == null || messageState == message.state }
                    .maxByOrNull(Message::timestamp)
            }
            .map { ChatItem(users[it.senderId]?.avatarUrl, getMessage(it), it.state) }
            .toList()

    fun getMessagesCountFromUser(userId: Int) =
        chats.values.sumOf { it.messagesFromUser(userId).count() }
    fun getMessagesCountFromUser(userId: Int, chatId: Int) =
        chats[chatId]?.messagesFromUser(userId)?.count() ?: 0

    private fun getMessage(it: Message): String =
        if (it.state !is MessageState.Deleted) it.text else with(it) {
            "Сообщение было удалено ${users[senderId]?.name}"
        }

    companion object {
        private fun Message.isValid() = checkNonNull(id, senderId, text, timestamp, state)

        private fun User.isValid() = checkNonNull(id, name)

        private fun Chat.isValid() = checkNonNull(id, messageIds, userIds)

        private fun checkNonNull(vararg elements: Any?) = elements.all { it != null }
    }
}