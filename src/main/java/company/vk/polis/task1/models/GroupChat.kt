package company.vk.polis.task1.models

class GroupChat(private val id: Int?, val avatarUrl: String?, val userIds: List<Int?>, val messageIds: List<Int?>) : Entity {
    override fun getId() = id
}
