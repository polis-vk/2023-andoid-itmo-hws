package company.vk.polis.task1

data class GroupChat(
    val id: Int,
    val userIds: List<Int>,
    override val messageIds: List<Int?>?,
    val avatarUrl: String?
) : ChatInterface {
    override fun getId() = id
}