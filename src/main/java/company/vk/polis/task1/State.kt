package company.vk.polis.task1

sealed class State {
    class READ() : State()
    class UNREAD : State()
    class DELETED(val userId: Int) : State()

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is State) return false
        return when(other) {
            is READ -> this is READ
            is UNREAD -> this is UNREAD
            is DELETED -> this is DELETED
        }
    }
}