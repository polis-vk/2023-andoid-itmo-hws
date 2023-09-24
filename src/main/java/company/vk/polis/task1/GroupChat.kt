package company.vk.polis.task1

class GroupChat(private val id: Int, val avatarUrl: String?, val userIds: List<Int>, private val messageIds: List<Int>) : Entity, ChatEntity {
    override fun getId(): Int {
        return id
    }

    override fun getMessageIds(): List<Int> {
        return messageIds
    }
}
