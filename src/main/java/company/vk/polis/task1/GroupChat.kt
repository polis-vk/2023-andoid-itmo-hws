package company.vk.polis.task1

data class GroupChat(val groupChatId: Int?, val userIds: List<Int?>?, val messageIds: List<Int?>?, val avatarUrl: String?) : ChatInterface {
    override fun getId(): Int? {
        return groupChatId
    }

    override fun getChatMessageIds(): List<Int?>? {
        return messageIds
    }

    override fun getChatUserIds(): List<Int?>? {
        return userIds
    }
}
