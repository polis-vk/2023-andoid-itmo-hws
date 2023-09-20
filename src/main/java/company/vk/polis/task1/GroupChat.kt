package company.vk.polis.task1

class GroupChat(val id: Int, val userIds: List<Int>, val messageIds: List<Int>) : Entity {
    override fun getId() = id
}
