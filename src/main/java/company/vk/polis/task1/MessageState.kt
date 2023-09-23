package company.vk.polis.task1

sealed class MessageState {
    data object Read : MessageState()
    data object Unread : MessageState()
    class Deleted(val userId: Int) : MessageState()
}
