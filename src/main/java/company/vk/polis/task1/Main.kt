package company.vk.polis.task1

fun main() {
    val entities: List<Entity> = Repository.getInfo()
    val messageController = MessageController(entities)
    val userId = 0
    val items = messageController.getChatItems(userId)
    val cntUnreadMessage = messageController.getNumberOfMessages(userId)

    println("Всего не прочитанных: ${cntUnreadMessage}\n")
    println("--------------------")
    for (item in items) {
        item.show(messageController.users)
        println("--------------------")
    }
}