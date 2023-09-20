package company.vk.polis.task1

internal data class ChatItem(
        val avatarUrl: String?,
        val groupAvatarUrl: String?,
        val lastMessage: Message,
        val lastMessageAuthor: Int,
        val state: State
) {
    override fun toString(): String {
        if (state.getState() == StateEnum.DELETED) {
            return "Сообщение было удалено $lastMessageAuthor"
        }
        return "Avatar=${avatarUrl}\nGroupAvatar=${groupAvatarUrl}\n" +
                "LastMessage={${lastMessage.text}}\nState=${state}"
    }
}