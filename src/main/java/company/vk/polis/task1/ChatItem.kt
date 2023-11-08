package company.vk.polis.task1

internal data class ChatItem(val avatarUrl: String?, val lastMessage: Message, val state: State) {
    fun show(users: Map<Int, User>) {
        if (state is State.Deleted) {
            println("Сообщение было удалено ${users[state.userId]?.name}.")
        } else {
            println(lastMessage.text)
        }
    }
}