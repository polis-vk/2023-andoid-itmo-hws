package company.vk.polis.task1.models

import org.jetbrains.annotations.Nullable

data class GroupChat(val id: Int, val userIds: List<Int>, val messageIds: List<Int>, @Nullable val uri: String?)
    : ChatEntity
{
    override fun getId(): Int {
        return id
    }

    override fun isValid(): Boolean {
        return true
    }

    override fun checkUser(userId: Int): Boolean {
        return userIds.contains(userId)
    }

    override fun getMessageIds(): List<Int> {
        return messageIds
    }
}