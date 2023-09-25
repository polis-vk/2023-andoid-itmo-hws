package company.vk.polis.task1

fun main() {
    //TODO()
    val controller = MessageController()
    println(controller.getUserChatItems(1))
    println(controller.getUserChatItems(1, State.Unread()))
    println(controller.getUserChatItems(1, State.Deleted(1)))
    println(controller.sendedMessagesCount(1))
}
