package company.vk.polis.task1.models

interface VerifiedChat {
    val avatarUrl: String?
    val id: Int
    val userIds: List<Int>
    val messageIds: List<Int>
    operator fun contains(userId: Int): Boolean
}
