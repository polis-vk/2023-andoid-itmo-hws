package company.vk.polis.task1

private val messageController = MessageController()

fun main() {
//    for (chat in messageController.getChatsView(0)) {
//        println(chat)
//        println()
//    }

    for (chat in messageController.getChatsView(0, MessageState(MessageStateEnum.DELETED))) {
        println(chat)
        println()
    }

}