package company.vk.polis.task1

import org.jetbrains.annotations.Nullable

data class GroupChat(
    val groupChatId: Int?,
    override val userIds: List<Int?>?,
    override val messageIds: List<Int?>?,
    @Nullable val avatarUrl: String?,
) : ChatInterface {
    override fun getId(): Int? {
        return groupChatId
    }
}
