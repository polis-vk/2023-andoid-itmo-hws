package company.vk.polis.task1

internal class MessageController(entities: List<Entity>) {
    val users: MutableMap<Int, User> = HashMap()
    private val chats: MutableMap<Int, Chat> = HashMap()
    private val groupChats: MutableMap<Int, GroupChat> = HashMap()
    private val messages: MutableMap<Int, Message> = HashMap()

    init {
        for (entity in entities) {
            when {
                isValidMessage(entity) -> messages[entity.id] = entity as Message
                isValidChat(entity) -> chats[entity.id] = entity as Chat
                isValidGroupChat(entity) -> groupChats[entity.id] = entity as GroupChat
                isValidUser(entity) -> users[entity.id] = entity as User
            }
        }
    }

    fun getChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val chats: List<Chat> = getChatsForUser(userId)
        val groupChats: List<GroupChat> = getGroupChatsForUser(userId)
        val items: MutableList<ChatItem> = ArrayList()
        for (chat in chats) {
            addAllChatItems(chat.messageIds, items)
        }
        for (groupChat in groupChats) {
            addAllChatItems(groupChat.messageIds, items)
        }
        return if (state != null)
            items.filter { state == it.state }
        else
            items
    }

    fun getNumberOfMessages(userId: Int): Int {
        val chats = getChatsForUser(userId)
        val groupChats = getGroupChatsForUser(userId)
        val messageIds: MutableSet<Int> = HashSet()
        for (chat in chats) {
            messageIds.addAll(chat.messageIds)
        }
        for (groupChat in groupChats) {
            messageIds.addAll(groupChat.messageIds)
        }
        return messages.map { it.value }.filter { messageIds.contains(it.id) && it.state is State.UNREAD }.size
    }

    private fun addAllChatItems(messageIds: List<Int>, items: MutableList<ChatItem>) {
        val setIds: Set<Int> = messageIds.toSet()
        val lastMessage: Message? = messages.map { it.value }.filter { m ->
            setIds.contains(m.id)
        }.sortedBy { it.timestamp }.toList().lastOrNull()
        if (lastMessage != null) {
            items.add(ChatItem(users[lastMessage.id]?.avatarUrl, lastMessage, lastMessage.state))
        }
    }

    private fun getGroupChatsForUser(userId: Int): List<GroupChat> {
        return groupChats.map { it.value }.filter { it.usersIds.contains(userId) }
    }


    private fun getChatsForUser(userId: Int): List<Chat> {
        return chats.map { it.value }.filter { e ->
            e.userIds.senderId == userId || e.userIds.receiverId == userId
        }
    }

    private fun isValidUser(entity: Entity): Boolean {
        if (entity is User) {
            return entity.id != null && entity.name != null
        }
        return false
    }

    private fun isValidChat(entity: Entity): Boolean {
        if (entity is Chat) {
            return entity.id != null && entity.userIds != null && entity.messageIds != null
        }
        return false
    }

    private fun isValidMessage(entity: Entity): Boolean {
        if (entity is Message) {
            return entity.id != null && entity.text != null && entity.senderId != null &&
                    entity.timestamp != null && entity.state != null
        }
        return false
    }

    private fun isValidGroupChat(entity: Entity): Boolean {
        return entity is GroupChat
    }
}