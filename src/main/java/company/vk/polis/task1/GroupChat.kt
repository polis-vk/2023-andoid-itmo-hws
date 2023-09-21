package company.vk.polis.task1

import company.vk.polis.task1.*

data class GroupChat(val id: Int, val userIds: List<Int>?, val messageIds: List<Int>?, var avatarUrl : String?) : Entity {
    override fun getId(): Int {
        return id
    }

}