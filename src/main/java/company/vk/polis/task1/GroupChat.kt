package company.vk.polis.task1

data class GroupChat(
        val id: Int,
        val avatarUrl: String?,
        override val messageIds: MutableList<Int>?,
        val userIds: MutableList<Int>
) : Entity, ChatInterface {
    override fun getId(): Int = id
}
