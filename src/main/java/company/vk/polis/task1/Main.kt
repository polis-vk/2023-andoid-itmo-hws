package company.vk.polis.task1

fun main() {
    val info = MessageController.getValidInfo()
    println(info)
    println("All chats:")
    for (chatItem in MessageController.getUserChatItems(1)) {
        println("chatId=${chatItem.chatId} msg=${chatItem.getMessageView()}" +
                " pic=${chatItem.avatarUrl} type=${chatItem.lastMessage?.state?.javaClass}")
    }
    println("Unreaded chats:")
    for (chatItem in MessageController.getUserChatItems(1, State.UNREADED())) {
        println("chatId=${chatItem.chatId} msg=${chatItem.getMessageView()}" +
                " pic=${chatItem.avatarUrl} type=${chatItem.lastMessage?.state?.javaClass}")
    }
    println("user 1 has ${MessageController.getUserMessageCount(1)} messages")
}