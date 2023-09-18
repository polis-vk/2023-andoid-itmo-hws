package company.vk.polis.task1

internal data class ChatItem(val avatarUrl: String?, val lastMessage: Message?, val state: State?) {
    fun show(users: Map<Int, User>) {
        if (state is State.DELETED) {
            println("Сообщение было удалено ${users[state.userId]?.name}.")
        } else if(lastMessage != null) {
            println(lastMessage.text)
        } else {
            println("Сообщений нет.")
        }
    }
}