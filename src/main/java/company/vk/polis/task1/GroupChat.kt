package company.vk.polis.task1

internal class GroupChat(
    private val id: Int,
    val userIds: List<Int>,
    @JvmField val messageIds: List<Int>,
    val name : String,
    val avatarUrl: String?,
) : BaseChat {
    override fun getMessageIds(): List<Int> {
        return messageIds
    }

    override fun getId() = id

    fun getNumberUsers() = userIds.size

    fun containsUser(userId : Int) = userIds.contains(userId)
}