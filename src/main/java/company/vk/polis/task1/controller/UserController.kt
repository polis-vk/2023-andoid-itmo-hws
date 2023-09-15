package company.vk.polis.task1.controller

import company.vk.polis.task1.entity.Chat
import company.vk.polis.task1.entity.Message
import company.vk.polis.task1.entity.User
import company.vk.polis.task1.service.ChatService
import company.vk.polis.task1.service.GroupChatService
import company.vk.polis.task1.service.MessageService
import company.vk.polis.task1.service.UserService

object UserController {
    fun register(name: String) = UserService.register(name, "/user/avatar/$name.png")

    fun createChat(user: User, friend: User, name: String) =
        ChatService.createChat(user.id, friend.id, "/chat/avatat/$name.png")

    fun createGroupChat(user: User, name: String, vararg otherUsers: User) =
        GroupChatService.createGroupChat(otherUsers.map { it.id } + user.id, "/chat/avatar/$name.png")

    fun write(user: User, chat: Chat, text: String?) = MessageService.write(user.id, chat.id, text)

    fun read(user: User, message: Message) = MessageService.read(message.id)

    fun deleteMessage(user: User, message: Message) = MessageService.delete(message.id, user.id)
}
