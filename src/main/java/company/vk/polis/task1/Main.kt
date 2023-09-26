package company.vk.polis.task1

fun main() {
    val messageController = MessageController()
    val info = messageController.getValidInfo()
    println(info)
    println("All chats:")
    messageController.getUserChatItems(1).forEach(::println)
    println("Unread chats:")
    messageController.getUserChatItems(1, State.UNREAD).forEach(::println)
    println("user 1 has ${messageController.getUserMessageCount(1)} messages")
}