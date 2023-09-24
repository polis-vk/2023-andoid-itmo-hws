package company.vk.polis.task1

class GroupChat(private val id: Int, val avatarUrl: String?, val userIds: List<User>, val messageIds: List<Int>) : Entity {
    override fun getId(): Int {
        return id
    }
}
