package company.vk.polis.task1


data class ChatItem(
    val avatarUrl: String?,
    val lastMessage: String,
    val lastTime: Long,
    val chatid : Int,
    val state: State
) {
    fun getDeletedMessageUrl(): Unit {
        if (state == State.DELETED) {
            println("Сообщение было удалено ${avatarUrl}")
        }
    }
}