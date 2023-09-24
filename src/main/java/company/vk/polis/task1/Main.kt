package company.vk.polis.task1

fun main() {
    val info = Repository.getInfo()
    val messageController = MessageController(info)
    val userId = 1

    val preview = messageController.getPreviewChats(userId)

    println("Preview chats by userId $userId:")
    for (i in 0 until minOf(10, preview.size)) {
        val chatItem = preview[i]
        println("Element $i: Avatar URL: ${chatItem.avatarUrl?.padEnd(3) ?: "N/A".padEnd(3)}, Label: ${chatItem.label}")
    }
    println()

    val count = messageController.countMessagesByUserId(1)
    println("Count message by userId $userId: $count")
}