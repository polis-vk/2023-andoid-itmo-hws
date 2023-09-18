package company.vk.polis.task1

sealed class State {
    class READ : State()
    class UNREAD : State()
    class DELETED(val usId : Int) : State()
}