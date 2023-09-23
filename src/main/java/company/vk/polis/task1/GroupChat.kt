package company.vk.polis.task1

data class GroupChat(val id: Int, val avatarUrl: String?, val userIds: List<Int>, val messageIds: List<Int>) : Entity, ChatInterface {
    override fun getId(): Int {
        return id
    }

    override fun getMessageIds(): List<Int> {
        return messageIds
    }

    override fun getSenderIds(): List<Int> {
        return userIds
    }
}