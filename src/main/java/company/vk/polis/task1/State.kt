package company.vk.polis.task1

sealed class State() {
    class Read: State()
    class Unread: State()
    class Deleted(val idDeleted: Int = -1): State()
}