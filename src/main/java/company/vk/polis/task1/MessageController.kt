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
        val validEntities = entities.filter { entity -> entity is KotlinEntity && entity.checkValid() }
        initializeData(validEntities)
        filterMessages(userData)
        filterAllChats(userData)
        countAllUsers()
    }

    private fun initializeData(entities: List<Entity>) {
        for (entity in entities) {
            when (entity) {
                is User -> {
                    UserId.add(entity.id)
                    userData.users.add(entity)
                }
                is Message -> {
                    MessageId.add(entity.id)
                    userData.messages.add(entity)
                }
                is Chat -> {
                    userData.allgroup.add(entity)
                }
                is GroupChat -> {
                    userData.allgroup.add(entity)
                }
            }
        }
    }

    private fun filterMessages(userData: UserData) {
        userData.messages = userData.messages.filter { message ->
            val userExists = userData.users.any { it.id == message.senderId }
            val chatExists =
                userData.allgroup.any { it.getListUsersId().contains(message.senderId)} // || it.getListUsersId().contains(message.receiverId)
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
                val name = user!!.name
                println("Сообщения было удалено$name")
                continue;
            }
            val chatItem = ChatItem(user?.avatarUrl, lastUserMessageInChat, lastUserMessageInChat.timestamp, chat.id, lastUserMessageInChat.state)
            chatItems.add(chatItem)
        }
        return chatItems
    }


    fun GetListChat(state: State): List<AllChats> { // I don't remember what I needed this method for, but I'll leave it
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
