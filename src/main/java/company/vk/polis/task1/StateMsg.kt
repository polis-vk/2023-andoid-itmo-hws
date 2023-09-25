package company.vk.polis.task1

sealed class StateMsg {
    data object READ : StateMsg()
    data object UNREAD : StateMsg()
    class DELETED(val id : Int) : StateMsg()
}
