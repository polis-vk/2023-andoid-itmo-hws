package company.vk.polis.task1

sealed class State {
    data object READ : State()
    data object UNREAD : State()
    data class Deleted(val userId: Int = -1) : State()
}
