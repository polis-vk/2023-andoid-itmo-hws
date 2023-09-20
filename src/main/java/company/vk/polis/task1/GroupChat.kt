package company.vk.polis.task1

data class GroupChat(
    val id: Int,
    val avatarUrl: String?,
    val memberIds: List<Int>,
    val messageIds: List<Int>
) : Entity { override fun getId(): Int = id }