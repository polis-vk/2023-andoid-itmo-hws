package company.vk.polis.task1

data class ChatItem(
    val avatarUrl: String?,
    val lastMessageId: String?,
    val lastMessageSentById: String?,
    val messageState: String?
) {
    override fun toString(): String {
        return "Avatar url: $avatarUrl\n " +
                "Last message: $lastMessageId\n " +
                "Sent by: $lastMessageSentById\n " +
                "Message state: $messageState"
    }
}