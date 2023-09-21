package company.vk.polis.task1

data class ChatItem(val avatarUrl: String?, var lastMessage: Message) {
    fun getLabel(getUserName: (Int) -> String): String {
        return when (val messageState = lastMessage.state){
            is Deleted -> "The message has been deleted ${getUserName(messageState.userId)}"
            else -> lastMessage.text
        }
    }
}
