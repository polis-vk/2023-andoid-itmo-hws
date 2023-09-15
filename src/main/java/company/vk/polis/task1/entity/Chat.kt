package company.vk.polis.task1.entity

open class GroupChat @JvmOverloads constructor(
    override val id: Int,
    val userIds: MutableList<Int>,
    val messageIds: MutableList<Int> = mutableListOf(),
    val avatarUrl: String? = null,
) : Entity

class Chat @JvmOverloads constructor(
    override val id: Int,
    user1: Int,
    user2: Int,
    messageIds: MutableList<Int> = mutableListOf(),
    avatarUrl: String? = null,
) : GroupChat(id, mutableListOf(user1, user2), messageIds, avatarUrl)
