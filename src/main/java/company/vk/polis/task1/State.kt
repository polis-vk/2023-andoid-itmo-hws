package company.vk.polis.task1

sealed class State {
    class READ : State() {
        override fun toString() : String {
            return "READ"
        }
    }
    class UNREAD : State() {
        override fun toString() : String {
            return "UNREAD"
        }
    }
    class DELETED(val usId : Int) : State() {
        override fun toString() : String {
            return "deleted by user $usId"
        }
    }
}