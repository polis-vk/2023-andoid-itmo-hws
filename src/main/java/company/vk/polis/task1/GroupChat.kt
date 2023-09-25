package company.vk.polis.task1

data class GroupChat(val id: Int, val avatarUrl: String?, val userIds: List<Int>, val messageIds: List<Int>) : Entity {
    override fun getId(): Int {
        return id
    }
}
