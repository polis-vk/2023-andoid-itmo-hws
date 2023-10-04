package company.vk.polis.task1

import java.util.*
import kotlin.collections.LinkedHashMap


data class UserData(
    val users: MutableList<User>,
    var messages: MutableList<Message>,
    var allgroup: MutableList<AllChats>

)

class MessageController() {
    val UserId = LinkedList<Int>()
    val MessageId = LinkedList<Int>()
    val UserId_Count = LinkedHashMap<Int, Int>()

    val userData = UserData(
        users = mutableListOf(),
        messages = mutableListOf(),
        allgroup = mutableListOf()
    )

    fun init (entities: List<Entity>) {
        initializeData(entities)
        filterMessages(userData)
        filterAllChats(userData)
        countAllUsers()
    }

    private fun initializeData(entities: List<Entity>) {
        for (entity in entities) {
            when (entity) {
                is User -> {
                    if (entity.id != null  && entity.name != null){
                        UserId.add(entity.id)
                        userData.users.add(entity)
                    }
                }
                is Message -> {
                    if (entity.id != null && entity.timestamp != null && entity.text != null && entity.senderId != null && entity.senderId >= 0 && entity.timestamp >= 0 && entity.state != null){
                        MessageId.add(entity.id)
                        userData.messages.add(entity)
                    }

                }
                is Chat -> {
                    if (entity.id != null && entity.userIds != null && entity.messageIds != null){
                        userData.allgroup.add(entity)
                    }
                }
                is GroupChat -> {
                    if (entity.id != null && entity.Link_Avatar != null && entity.usersid != null && entity.messageIds != null){
                        userData.allgroup.add(entity)
                    }
                }
            }
        }
    }

    private fun filterMessages(userData: UserData) {
        userData.messages = userData.messages.filter { message ->
            val userExists = userData.users.any { it.id == message.senderId }
            val chatExists =
                userData.allgroup.any { it.getListUsersId().contains(message.senderId)}
            userExists && chatExists
        }.toMutableList()
    }

    private fun filterAllChats(userData: UserData) {
        val chatsToRemove = mutableListOf<AllChats>()
        for (chat in userData.allgroup) {
            val users = chat.getListUsersId().intersect(UserId)
            val messages = chat.getListMessagesId().intersect(MessageId)
            if (users.size < 2 || messages.isEmpty()) {
                chatsToRemove.add(chat)
            }
        }
        userData.allgroup.removeAll(chatsToRemove.toSet())
    }

    fun GetListMessage(user_id: Int, state: State? = null): List<ChatItem> {
        val chatItems = mutableListOf<ChatItem>()
        for (chat in userData.allgroup) {
            if(!chat.getListUsersId().contains(user_id)) {
                continue
            }
            val userMessagesInChat = userData.messages.filter {
                it.senderId == user_id && it.id == chat.id && (state == null || it.state == state)
            }
            val lastUserMessageInChat = userMessagesInChat.maxByOrNull { it.timestamp } ?: continue
            var user = userData.users.find { it.id == user_id }
            if(lastUserMessageInChat.state is State.DELETED) {
                val id = (lastUserMessageInChat.state as State.DELETED).userId
                user = userData.users.find { it.id == id }
                val name = user?.name
                val deletedMessageText = "Сообщение было удалено$name"
                val updatedMessage = Message(
                    lastUserMessageInChat.id,
                    deletedMessageText,
                    lastUserMessageInChat.senderId,
                    lastUserMessageInChat.timestamp,
                    lastUserMessageInChat.state
                )
                val chatItem = ChatItem(user?.avatarUrl, updatedMessage, lastUserMessageInChat.timestamp, chat.id, lastUserMessageInChat.state)
                chatItems.add(chatItem)
                continue;
            }
            val chatItem = ChatItem(user?.avatarUrl, lastUserMessageInChat, lastUserMessageInChat.timestamp, chat.id, lastUserMessageInChat.state)
            chatItems.add(chatItem)
        }
        return chatItems
    }


    fun GetListChat(state: State): List<AllChats> {
        val out = mutableListOf<AllChats>()

        for (chat in userData.allgroup) {
            val lastMessage = userData.messages.maxByOrNull { it.timestamp }
            if (lastMessage != null && lastMessage.state == State.UNREAD) {
                out.add(chat)
            }
        }

        return out
    }

    private fun CountByUser(user_id: Int): Int { // for task 2.3
        return userData.messages.count { message ->
            message.senderId == user_id
        }
    }

    private fun countAllUsers() {
        val messageCountsByUser = mutableMapOf<Int, Int>()

        for (user in userData.users) {
            val messageCount = CountByUser(user.id)
            messageCountsByUser[user.id] = messageCount
        }
        UserId_Count.putAll(messageCountsByUser)
    }

    fun GetCount(user_id: Int): Int {
        val count = UserId_Count[user_id]
        if (count == null) {
            println("Пользователя с ID $user_id не существует.")
        }
        return count ?: 0
    }

}
