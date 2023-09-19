package company.vk.polis.task1

data class GroupChat(
    val id: Int,
    val userIds: List<Int>,
    val messageIds: List<Int>,
    val avatarUrl: String?
) : Entity {
    override fun getId() = id
}