package company.vk.polis.task1


fun main() {
    val messageController = MessageController()
    val chatItems = messageController.getChatItems(1)
    println(chatItems.get(0).lastMessage)
}