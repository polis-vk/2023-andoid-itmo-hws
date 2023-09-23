package company.vk.polis.task1

sealed interface State {
    data object UNREAD : State
    data object READ : State
    data class DELETED(val id: Int) : State
}