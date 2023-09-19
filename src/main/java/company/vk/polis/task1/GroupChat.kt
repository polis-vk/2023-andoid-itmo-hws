package company.vk.polis.task1

data class GroupChat(
    @JvmField val id: Int,
    val avatarUrl: String?,
    @JvmField val userIds: List<Int>,
    @JvmField val messageIds: List<Int>
) : MetaChat {
    override fun getId(): Int {
        return id
    }

    override fun getUserIds(): List<Int> {
        return userIds
    }

    override fun getMessageIds(): List<Int> {
        return messageIds
    }
}