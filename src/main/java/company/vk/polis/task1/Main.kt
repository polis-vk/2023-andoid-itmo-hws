package company.vk.polis.task1


fun main() {
    val entities = DataUtils.generateEntity()
    val messageController = MessageController()

    messageController.init(entities)

    println("Users:")
    for (user in messageController.userData.users) {
        println("User ID: ${user.id}, Name: ${user.name}, Avatar URL: ${user.avatarUrl}")
    }

    println("Messages:")
    for (message in messageController.userData.messages) {
        println("Message ID: ${message.id}, Text: ${message.text}, Sender ID: ${message.senderId}, Time: ${message.timestamp} State : ${message.state}")
    }

    println("AllChats:")
    for (chat in messageController.userData.allgroup) {
        println("Chat ID: ${chat.id}, Avatar : ${chat.getAvatar()}, User IDs: ${chat.getListUsersId()}, Message IDs: ${chat.getListMessagesId()}")
    }

    println("Count:")
    println(messageController.GetCount(0))


    println("2.2")
    val ex = messageController.GetListMessage(0, State.UNREAD);
    for (i in ex){
        println("Avatar URL: ${i.avatarUrl}, Last Message : ${i.lastMessage}, Last Time : ${i.lastTime}, chatid : ${i.chatid}, State : ${i.state}")
    }
}
