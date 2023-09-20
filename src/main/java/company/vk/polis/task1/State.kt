package company.vk.polis.task1

sealed class State {

    class READ : State() {
        override fun equals(other: Any?): Boolean {
            return other is READ
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

    }
    class UNREAD : State() {
        override fun equals(other: Any?): Boolean {
            return other is UNREAD
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
    class DELETE(val id: Int) : State() {
        override fun equals(other: Any?): Boolean {
            return other is DELETE
        }

        override fun hashCode(): Int {
            return id
        }

    }

}