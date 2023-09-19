package company.vk.polis.task1

class MessageController {

    companion object {
        val ZAGLUSHKA: String = "zaglushka"
    }

    private fun entityChecker(entity: Entity) : Boolean {
        return when(entity) {
            is Chat -> !listOf(entity.messageIds, entity.userIds, entity.id).any {it == null}
            is Message -> !listOf(entity.senderId, entity.id, entity.text, entity.timestamp).any {it == null}
            is User -> !listOf(entity.name, entity.id).any {it == null}
            is Konfa -> true //невозможно создать иначе объект
            else -> false;
        }
    }

    // можно, пожалуйста, подсказку, как сделать ChatItem не internal, но принимающего Message? делать Message public не хочется
    internal class ChatItem (val lastMessage: Message, val userName : String, var avurl : String? = ZAGLUSHKA) {
        override fun toString(): String {
            return when(lastMessage.state) {
                is State.DELETED -> "Message ${lastMessage.state}         для дебага: \"${lastMessage.id}\""
                else -> "User with avatar = \"$avurl\" and with name \"$userName\"\nsend message                 для дебага: \"${lastMessage.id}\"" +
                        " \"${lastMessage.text}\"\nwith status ${lastMessage.state}"
            }
        }
    }

    private val validEntities : List<Entity> = Repository.getInfo().filter { e -> entityChecker(e) }
    private val dictOfUsers : Map<Int, User> = validEntities.filterIsInstance<User>().map { it.id to it }.toMap();
    private val dictOfMessage : Map<Int, Message> = validEntities.filterIsInstance<Message>().map { it.id to it }.toMap();

    internal fun getItemsForUser(userId: Int, state: State? = null): List<ChatItem> {
        val possibleChats: List<Chatable> = validEntities.filterIsInstance<Chatable>()
                .filter{ e -> e.getUsers().contains(userId) }.toList() //чаты, где он числится отправителем

        val list: MutableList<ChatItem> = mutableListOf();

        val user = dictOfUsers[userId] ?: return list

        for (chat in possibleChats) {
            val possibalItems = chat.getMessages().map { it -> dictOfMessage[it]!! }
                    .filter {e -> e.senderId == userId && (state == null || e.state == state)}
                    .map { e -> ChatItem(e, user.name, user.avatarUrl) }
            if (possibalItems.any()) {
                list.addAll(possibalItems)
            }
        }
        return list
    }

    fun getCountOfSendMesById(userId: Int) : Int {
        return getItemsForUser(userId).size
    }
}