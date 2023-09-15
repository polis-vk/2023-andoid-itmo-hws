package company.vk.polis.task1.service

import company.vk.polis.task1.entity.*
import company.vk.polis.task1.repository.Repository
import company.vk.polis.task1.repository.StorageRepository

private class EntityNotFoundException(message: String) : RuntimeException(message)

private val repo: Repository = StorageRepository

fun currentInfo() = repo.info

private inline fun <reified E : Entity> find(id: Int): E =
    repo.info.filterIsInstance<E>().find { it.id == id }
        ?: throw EntityNotFoundException("Entity [${E::class.simpleName}] with id=`$id` was not found")

private object ID {
    private var id = 0

    fun new() = id++
}

object UserService {
    fun findById(id: Int) = find<User>(id)

    fun register(name: String, avatarUrl: String?) = User(ID.new(), name, avatarUrl).also { repo.addEntity(it) }
}

object MessageService {
    fun findById(id: Int) = find<Message>(id)

    fun write(senderId: Int, chatId: Int, text: String?): Message {
        val user = UserService.findById(senderId)
        val chat = ChatService.findById(chatId)

        val newMessage = Message(ID.new(), text, user.id, System.currentTimeMillis())

        user.lastMessage = newMessage
        chat.messageIds += newMessage.id

        repo.addEntity(newMessage)

        return newMessage
    }

    fun read(id: Int) = findById(id).read()

    fun delete(id: Int, userId: Int) = findById(id).delete(userId)
}

object ChatService {
    fun findById(id: Int) = find<Chat>(id)

    fun createChat(owner: Int, friend: Int, avatarUrl: String?): Chat {
        val fst = UserService.findById(owner)
        val snd = UserService.findById(friend)

        return Chat(ID.new(), fst.id, snd.id, mutableListOf(), avatarUrl).also { repo.addEntity(it) }
    }
}

object GroupChatService {
    fun findById(id: Int) = find<GroupChat>(id)

    fun createGroupChat(userIds: List<Int>, avatarUrl: String?): GroupChat {
        val users = userIds.map { UserService.findById(it) }

        return GroupChat(ID.new(), users.map { it.id }.toMutableList(), mutableListOf(), avatarUrl).also { repo.addEntity(it) }
    }

    fun addUser(id: Int, userId: Int): GroupChat = findById(id).apply { userIds += userId }
}
