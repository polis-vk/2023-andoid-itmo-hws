package company.vk.polis.task1

import java.util.*
import kotlin.collections.LinkedHashMap


data class UserData(
    val users: MutableList<User>,
    var messages: MutableList<Message>,
    var chats: MutableList<Chat>,
    var grouping: MutableList<GroupChat>,
    var allgroup : MutableList<AllChats>
)

class MessageController() {
    var UserId = LinkedList<Int>()
    var MessageId = LinkedList<Int>()
    var UserId_Count = LinkedHashMap<Int, Int>()

    val userData = UserData(
        users = mutableListOf(),
        messages = mutableListOf(),
        chats = mutableListOf(),
        grouping = mutableListOf(),
        allgroup = mutableListOf()
    )


    fun main(entities: List<Entity>) : Unit {
        val validEntities = entities.filter { entity -> entity.Valid() }
        initializeData(validEntities)
        filterMessages(userData)
        filterChats(userData)
        filterGrChats(userData)
        unite_groups(userData)
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
                    userData.chats.add(entity)
                }
                is GroupChat -> {
                    userData.grouping.add(entity)
                }
            }
        }
    }

    private fun unite_groups(userData: UserData) {
        val allChats = mutableListOf<AllChats>()
        allChats.addAll(userData.chats)
        allChats.addAll(userData.grouping)

        userData.allgroup = allChats
    }



    private fun filterMessages(userData: UserData) {
        val messagesToRemove = mutableListOf<Message>()
        for (message in userData.messages) {
            val userExists = userData.users.any { it.id == message.senderId }
            val chatExists = userData.chats.any { it.userIds.senderId == message.senderId || it.userIds.receiverId == message.senderId }
            val grchatExists = userData.grouping.any {message.senderId in it.usersid}

            if (!userExists || (!chatExists && !grchatExists)) {
                messagesToRemove.add(message)
            }
        }

        userData.messages.removeAll(messagesToRemove)
    }

    private fun filterChats(userData: UserData) {
        var updchat = mutableListOf<Chat>()
        for (chat in userData.chats) {
            val user1Exists = userData.users.any { it.id == chat.userIds.senderId }
            val user2Exists = userData.users.any { it.id == chat.userIds.receiverId }

            var messages = chat.messageIds.intersect(MessageId)

            if (user1Exists && user2Exists && !messages.isEmpty()) {
                updchat.add(Chat(chat.id, chat.userIds, messages.toList()))
            }
        }
        userData.chats.clear()
        userData.chats.addAll(updchat)
    }

    private fun filterGrChats(userData: UserData) { // I apologize for the copy paste. I was thinking of deleting the chat class to create a List with user IDs instead of UserPair userIds,
        // but I didn't want to change your classes, so I decided to copy-paste
        val chatsToRemove = mutableListOf<GroupChat>()
        for (chat in userData.grouping) {
            val users = chat.usersid.intersect(UserId)
            val messages = chat.messageIds.intersect(MessageId)
            if (users.size < 2 || messages.isEmpty()) {
                chatsToRemove.add(chat)
            } else {
                chat.messageIds = messages.toList()
                chat.usersid = users.toList()
            }
        }
        userData.grouping.removeAll(chatsToRemove)
    }

    fun GetListMessage(user_id: Int, state: State? = null): List<ChatItem> { // I wanted to make a common interface for chat,
        // but then for some reason I couldn't get UserPair userIds from chat,
        // so in order not to worry, I decided to make a copy paste
        val chatItems = mutableListOf<ChatItem>()

        for (chat in userData.chats) {
            if (chat.userIds.senderId == user_id || chat.userIds.receiverId == user_id) {
                val userMessagesInChat = userData.messages.filter {
                    it.senderId == user_id && it.id == chat.id && (state == null || it.state == state)
                }

                val lastUserMessageInChat = userMessagesInChat.maxByOrNull { it.timestamp }

                if (lastUserMessageInChat != null) {
                    val user = userData.users.find { it.id == user_id }
                    if (lastUserMessageInChat.state == State.DELETED) {
                        println("Сообщение было удалено ${user?.avatarUrl ?: "неизвестным пользователем"}")
                    } else {
                        val chatItem = ChatItem(user?.avatarUrl, lastUserMessageInChat.text, lastUserMessageInChat.timestamp, chat.id, lastUserMessageInChat.state)
                        chatItems.add(chatItem)
                    }
                }
            }
        }

        for (chat in userData.grouping) {
            var flag = false;
            for (i in chat.usersid){
                if (i == user_id){
                    flag = true;
                }
            }
            if (flag) {
                val userMessagesInChat = userData.messages.filter {
                    it.senderId == user_id && it.id == chat.id && (state == null || it.state == state)
                }

                val lastUserMessageInChat = userMessagesInChat.maxByOrNull { it.timestamp }

                if (lastUserMessageInChat != null) {
                    val user = userData.users.find { it.id == user_id }
                    if (lastUserMessageInChat.state == State.DELETED) {
                        println("Сообщение было удалено ${user?.avatarUrl ?: "неизвестным пользователем"}")
                    } else {
                        val chatItem = ChatItem(user?.avatarUrl, lastUserMessageInChat.text, lastUserMessageInChat.timestamp, chat.id, lastUserMessageInChat.state)
                        chatItems.add(chatItem)
                    }
                }
            }
        }
        return chatItems
    }


    fun GetListChat(state: State) : List<AllChats>{
        val out = mutableListOf<AllChats>()

        for (chat in userData.chats) {
            val lastMessage = userData.messages.maxByOrNull { it.timestamp }
            if (lastMessage != null && lastMessage.state == State.UNREAD){
                out.add(chat)
            }
        }

        for (chat in userData.grouping) {
            val lastMessage = userData.messages.maxByOrNull { it.timestamp }
            if (lastMessage != null && lastMessage.state == State.UNREAD){
                out.add(chat)
            }
        }

        return out
    }


    private fun CountByUser(user_id: Int): Int { // for task 2.3
        return userData.messages.count { message ->
            message.senderId == user_id}
    }

    private fun countAllUsers(): Unit {
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
