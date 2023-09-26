package company.vk.polis.task1

fun main() {
    val messageController = MessageController()
    val info = messageController.getValidInfo()
    println(info)
    val userChats = messageController.getUserChatItems(1)
    println("All chats:")
    userChats.forEach(::println)
    println("As ChatItem view")
    userChats.forEach { println(it.getMessageView()) }
    println("Unread chats:")
    messageController.getUserChatItems(1, State.UNREAD).forEach(::println)
    println("user 1 has ${messageController.getUserMessageCount(1)} messages")
}