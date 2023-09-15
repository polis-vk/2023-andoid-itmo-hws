package company.vk.polis.task1.entity

data class Message(override val id: Int, val text: String?, val senderId: Int?, val timestamp: Long?) : Entity {
    sealed interface State
    object READ : State
    object UNREAD : State
    @JvmInline
    value class DELETED(private val userId: Int) : State

    var state: State = UNREAD
        private set

    fun read() {
        state = READ
    }

    fun delete(userId: Int) {
        state = DELETED(userId)

        println("> Message [$id] was deleted by user=$userId")
    }
}
