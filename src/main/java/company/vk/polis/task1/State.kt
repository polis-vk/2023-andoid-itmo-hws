package company.vk.polis.task1

sealed class State {
    class Read : State() { //Я хотел использовать data object вместо class, но java не понимает, что это такое
        override fun toString(): String {
            return "READ"
        }

        override fun equals(other: Any?): Boolean {
            return other is State.Read
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    class Unread : State() {
        override fun toString(): String {
            return "UNREAD"
        }

        override fun equals(other: Any?): Boolean {
            return other is State.Unread
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    data class Deleted(val id: Int?) : State() {
        override fun toString(): String {
            return "DELETED"
        }

        override fun equals(other: Any?): Boolean {
            return other is State.Deleted && id == other.id
        }

        override fun hashCode(): Int {
            return id ?: 0
        }
    }
}
