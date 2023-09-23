package company.vk.polis.task1

fun main() {
    val messageController = MessageController()
    val info = messageController.getValidInfo()
    println(info)
    println("All chats:")
    for (chatItem in messageController.getUserChatItems(1)) {
        println(chatItem)
    }
    println("Unread chats:")
    for (chatItem in messageController.getUserChatItems(1, State.UNREAD)) {
        println(chatItem)
    }
    println("user 1 has ${messageController.getUserMessageCount(1)} messages")
}