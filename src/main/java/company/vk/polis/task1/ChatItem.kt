package company.vk.polis.task1

class ChatItem(private val id : Int, var avatarUrl: String?, private var lastMessage: Message?, private var lastMessageState: MessageState?, getUserName: (Int) -> String?) : Entity {
    var label: String

    override fun getId(): Int {
        return id
    }

    init {
        label = getLabel(getUserName)
    }

    fun setLastMessage(lastMessage: Message?, lastMessageState: MessageState, getUserName: (Int) -> String?)
    {
        this.lastMessage = lastMessage
        this.lastMessageState = lastMessageState
        label = getLabel(getUserName)
    }

    private fun getLabel(getUserName: (Int) -> String?) : String
    {
        val messageState = lastMessageState
        return when {
            lastMessage == null -> "There's nothing here yet"
            messageState is MessageState.Deleted -> "The message has been deleted ${getUserName(messageState.userId) ?: "N/A"}"
            else -> lastMessage!!.text
        }
    }
}
