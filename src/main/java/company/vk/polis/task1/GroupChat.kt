package company.vk.polis.task1

class GroupChat(
    val id: Int,
    val userIds: List<Int>,
    override val messageIds: List<Int>,
    val name : String,
    val avatarUrl: String?,
) : BaseChat {

    override fun getId() = id

    fun getNumberUsers() = userIds.size

    fun containsUser(userId : Int) = userIds.contains(userId)
}