package company.vk.polis.task1

sealed class State {
    data object READ : State()
    data object UNREAD : State()
    data class DELETED(val userId: Int) : State() {
        override fun equals(other: Any?): Boolean {
            return this.javaClass == other?.javaClass
        }

        override fun hashCode(): Int {
            return DELETED.userId
        }
    }

    companion object {
        val DELETED: DELETED = DELETED(-1)
    }
}
