package company.vk.polis.task1

data class ChatItem(val avatarUrl: String?, val lastMessage: String, val state: State, val groupChat: Boolean, val deleterName: String?) {
    override fun toString(): String {
        if (deleterName == null)
            return "ChatItem(avatarUrl=$avatarUrl, lastMessage=$lastMessage, state=$state, groupChat=$groupChat)"
        return "Сообщение было удалено $deleterName"
    }
}
