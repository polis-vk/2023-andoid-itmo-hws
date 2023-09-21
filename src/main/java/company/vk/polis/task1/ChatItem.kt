package company.vk.polis.task1

internal data class ChatItem(
        val avatarUtl: String?,
        val lastMessage: Message,
        val state: State
) {
    override fun toString(): String {
        return when(state) {
            is State.READ -> "Последнее сообщение c id: ${lastMessage.id} прочитано"
            is State.UNREAD -> "Последнее сообщение c id: ${lastMessage.id} не прочитано"
            is State.DELETED -> {
                val mc = MessageController()
                val user = mc.userById(state.userId)
                "Сообщение было удалено ${user?.name ?: "неизвестным пользователем"}"
            }
        }
    }
}