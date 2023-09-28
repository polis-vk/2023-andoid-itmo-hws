package company.vk.polis.task1


internal class ChatItem(val message: Message, val name: String, val avatarUrl: String?, val numberUsers: Int? = null) {
    val state: State = message.state

    init {
        when (state) {
            is State.DELETED -> println("The message was deleted by user ${state.userId}")
            else -> {}
        }
    }
}
