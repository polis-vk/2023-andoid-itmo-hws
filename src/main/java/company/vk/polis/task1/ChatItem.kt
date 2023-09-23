package company.vk.polis.task1

class ChatItem(var avatarUrl: String?, var lastMessage: Message?, getUserName: (Int) -> String?) {
    var label: String

    init {
        val messageState = lastMessage?.state
        label = when {
            lastMessage == null -> "There's nothing here yet"
            messageState is MessageState.Deleted -> "The message has been deleted ${getUserName(messageState.userId)}"
            else -> lastMessage!!.text
        }
    }
}
