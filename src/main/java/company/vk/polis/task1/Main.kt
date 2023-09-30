package company.vk.polis.task1

fun main() {
    for (item in MessageController.getChatItems(3, State.UNREAD)) {
        println(item)
    }
    println(MessageController.getNumberMessages(0))

}