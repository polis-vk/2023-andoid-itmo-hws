package company.vk.polis.task1

fun main() {
    for (item in MessageController.getChatItems(0)) {
        println("${item.state} ${item.message} ${item.avatarUrl}")
    }

    println(MessageController.getNumberMessages(2))
}