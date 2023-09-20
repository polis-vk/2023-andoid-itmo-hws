package company.vk.polis.task1

data class GroupChat(val id: Int, val avatarUrl: String, val userIds: List<Int>, val messageIds: List<Int>) : Conversation {
    override fun getId() = id

    override fun messageIds(): List<Int> = messageIds
}
