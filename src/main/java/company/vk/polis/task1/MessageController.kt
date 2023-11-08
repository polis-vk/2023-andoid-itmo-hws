package company.vk.polis.task1

internal class MessageController {
    private val users: MutableMap<Int, User> = HashMap()
    private val chats: MutableList<Chat> = ArrayList()
    private val groupChats: MutableList<GroupChat> = ArrayList()
    private val messages: MutableList<Message> = ArrayList()

    init {
        val entities = Repository.getInfo()
        for (entity in entities) {
            when {
                isValidMessage(entity) -> messages.add(entity as Message)
                isValidChat(entity) -> chats.add(entity as Chat)
                isValidGroupChat(entity) -> groupChats.add(entity as GroupChat)
                isValidUser(entity) -> users[entity.id] = entity as User
            }
        }
    }

    fun getChatItems(userId: Int, state: State? = null): List<ChatItem> {
        val chats: List<Chat> = getChatsForUser(userId)
        val groupChats: List<GroupChat> = getGroupChatsForUser(userId)
        val allUserChats: MutableList<ChatItem> = ArrayList()
        for (chat in chats) {
            addAllChatItems(chat.messageIds, allUserChats)
        }
        for (groupChat in groupChats) {
            addAllChatItems(groupChat.messageIds!!, allUserChats)
        }
        return if (state != null)
            allUserChats.filter { state == it.state }
        else
            allUserChats
    }

    fun getNumberOfMessages(userId: Int): Int {
        return messages
            .filter { message -> message.senderId == userId }
            .size
    }

    private fun addAllChatItems(messageIds: List<Int>, items: MutableList<ChatItem>) {
        val setIds: Set<Int> = messageIds.toSet()
        val lastMessage: Message? = messages
            .filter { message -> setIds.contains(message.id) }
            .sortedBy { message -> message.timestamp }
            .toList()
            .lastOrNull()
        if (lastMessage != null) {
            items.add(ChatItem(users[lastMessage.id]?.avatarUrl, lastMessage, lastMessage.state))
        }
    }

    private fun getGroupChatsForUser(userId: Int): List<GroupChat> {
        return groupChats
            .filter { groupChat -> groupChat.usersIds!!.contains(userId) }
    }


    private fun getChatsForUser(userId: Int): List<Chat> {
        return chats
            .filter { chat -> chat.userIds.senderId == userId || chat.userIds.receiverId == userId }
    }

    private fun isValidUser(entity: Entity): Boolean {
        return entity is User && entity.id != null && entity.name != null
    }

    private fun isValidChat(entity: Entity): Boolean {
        return entity is Chat && entity.id != null && entity.userIds != null && entity.messageIds != null
    }

    private fun isValidMessage(entity: Entity): Boolean {
        return entity is Message && entity.id != null && entity.text != null && entity.senderId != null &&
                entity.timestamp != null && entity.state != null
    }

    private fun isValidGroupChat(entity: Entity): Boolean {
        return entity is GroupChat && entity.messageIds != null && entity.usersIds != null
    }

    fun getUsers(): Map<Int, User> {
        return users
    }
}