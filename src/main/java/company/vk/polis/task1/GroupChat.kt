package company.vk.polis.task1

data class GroupChat(val id: Int, val userId: Int, val receiverIds: List<Int>, val messageIdList: List<Int>, val avatarUrl: String?) : Entity, ChatInterface {
    override fun getId(): Int {
        return id
    }

    override fun getMessageIds(): List<Int> {
        return messageIdList
    }

    override fun getSenderId(): Int {
        return userId
    }

}