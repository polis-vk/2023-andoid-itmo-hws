package company.vk.polis.task1.models

sealed interface MessageState {
    data object Read : MessageState
    data object Unread : MessageState
    data class Deleted(val userId: Int) : MessageState
    companion object {
        @JvmStatic
        fun random(userIds: IntRange): MessageState = listOf(Read, Unread, Deleted(userIds.random())).random()
    }
}