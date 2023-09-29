package company.vk.polis.task1

fun main() {
    //TODO()
    val controller = MessageController()
    println(controller.getUserChatItems(1))
    println(controller.getUserChatItems(1, State.UNREAD))
    println(controller.getUserChatItems(2, State.DELETED(2)))
    println(controller.sendedMessagesCount(1))
}
