package company.vk.polis.task1.models

sealed class State {
    class READ : State()
    class UNREAD : State()
    class DELETED(val userId : Int) : State()
    class DEFAULT() : State()
}