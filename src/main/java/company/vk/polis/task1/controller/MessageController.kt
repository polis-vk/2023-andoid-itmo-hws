package company.vk.polis.task1.controller

import company.vk.polis.task1.models.*
import company.vk.polis.task1.repository.Repository
import company.vk.polis.task1.ui.ChatItem

class MessageController {

    private val messages: Map<Int, Message>
    private val users: Map<Int, User>
    private val chats: Map<Int, VerifiedChat>

    fun Chat.verify(): VerifiedChat? = if (!isValid()) null else object : VerifiedChat {
        override val avatarUrl by lazy {
            users[this@verify.userIds.senderId]?.avatarUrl
        }
        override val id = this@verify.id
        override val userIds = listOf(this@verify.userIds.receiverId, this@verify.userIds.senderId)
        override val messageIds = this@verify.messageIds.filterNotNull()

        override fun contains(userId: Int) = userIds.first() == userId
    }

    fun GroupChat.verify(): VerifiedChat? = if (!isValid()) null else object : VerifiedChat {
        override val avatarUrl = this@verify.avatarUrl
        override val id = this@verify.id!!
        override val userIds = this@verify.userIds.filterNotNull()
        override val messageIds = this@verify.messageIds.filterNotNull()

        override fun contains(userId: Int) = userId in userIds
    }

    init {
        val messages = mutableListOf<Message>()
        val users = mutableListOf<User>()
        val chats = mutableListOf<VerifiedChat>()

        Repository.getInfo().forEach {
            when (it) {
                is Message -> {
                    if (it.isValid()) messages += it
                }

                is User -> {
                    if (it.isValid()) users += it
                }

                is Chat -> it.verify()?.let(chats::add)

                is GroupChat -> it.verify()?.let(chats::add)
            }
        }

        this.messages = messages.associateBy { it.id }
        this.users = users.associateBy { it.id }
        this.chats = chats.associateBy { it.id }
    }

    fun chatIds(userId: Int): Sequence<Int> =
        chats.values.asSequence().filter { userId in it }.map { it.id }

    private fun VerifiedChat.messagesToUser(userId: Int): Sequence<Message> = if (contains(userId)) {
        messageIds.asSequence()
            .map { messages[it] }
            .filterNotNull()
    } else sequenceOf()

    fun getMessagesToUser(userId: Int, messageState: MessageState? = null): List<ChatItem> =
        chats.values.asSequence()
            .mapNotNull {
                it.messagesToUser(userId)
                    .maxByOrNull(Message::timestamp)
                    ?.takeIf { message -> messageState == null || messageState == message.state }
                    ?.let { message ->
                        message to it.avatarUrl
                    }
            }
            .map { ChatItem(it.second, getMessage(it.first), it.first.state) }
            .toList()

    fun getUnreadMessagesForUser(userId: Int) = getMessagesToUser(userId, MessageState.Unread).size

    fun getUnreadMessagesForChat(userId: Int, chatId: Int) =
        chats[chatId]?.let { if (userId in it) it.messageIds else listOf() }?.asSequence().orEmpty()
            .map { messages[it] }
            .filterNotNull()
            .sortedByDescending { it.timestamp }
            .takeWhile { it.state is MessageState.Unread }
            .count()

    private fun getMessage(it: Message): String =
        if (it.state !is MessageState.Deleted) it.text else with(it) {
            "Сообщение было удалено ${users[senderId]?.name}"
        }

    companion object {
        private fun Message.isValid() = checkNonNull(id, senderId, text, timestamp, state)

        private fun User.isValid() = checkNonNull(id, name)

        private fun Chat.isValid() =
            checkNonNull(id, messageIds, userIds) && checkNonNull(userIds.senderId, userIds.receiverId)

        private fun GroupChat.isValid() = checkNonNull(id, messageIds, userIds)
        private fun checkNonNull(vararg elements: Any?) = elements.all { it != null }
    }
}