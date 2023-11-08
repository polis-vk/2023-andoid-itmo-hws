package company.vk.polis.task1

fun main() {
    val messageController = MessageController()
    val userId = 0
    val items = messageController.getChatItems(userId)
    val sentMessages = messageController.getNumberOfMessages(userId)

    println("Всего отправленных: ${sentMessages}\n")
    println("--------------------")
    for (item in items) {
        item.show(messageController.getUsers())
        println("--------------------")
    }
}