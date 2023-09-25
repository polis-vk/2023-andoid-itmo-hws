package company.vk.polis.task1

data class ChatItem(
    val avatarUrl: String?,
    val usersLastMsg: String?,
    val lastMsgStatus: StateMsg,
    val userId: Int?
) {
    fun writeIfMsgDelete(): Unit {
        if(lastMsgStatus is StateMsg.DELETED) {
            println("Сообщение было удалено ${avatarUrl}")
        }
    }
}
