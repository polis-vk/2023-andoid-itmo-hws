package company.vk.polis.task1

sealed interface State {
    data object READ : State
    data object UNREAD : State
    data class DELETED(val userId: Int) : State
}