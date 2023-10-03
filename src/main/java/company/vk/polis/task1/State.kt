package company.vk.polis.task1

sealed class State {
    object Read: State()
    object Unread: State()
    class Deleted(val idDeleted: Int = -1): State()

}