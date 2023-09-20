package company.vk.polis.task1

data class GroupChat(val id: Int?, val userIds: List<Int?>?, val messageIds: List<Int?>?, val avatarUrl: String?) :
    Entity, ChatInterface {
    override fun getId(): Int? {
        return id
    }

    override fun getMessageIds(): List<Int?>? {
        return messageIds
    }

    override fun getUserIds(): List<Int?>? {
        return userIds
    }
}
