package company.vk.polis.task1

import java.rmi.UnexpectedException
import java.util.function.Function
import java.util.stream.Collectors

class MessageController {
    companion object {
        @JvmStatic
        private fun checkEntityCorrection(entity: Entity): Boolean {
            return when (entity) {
                is Chat -> entity.messageIds != null && entity.userIds != null && entity.id() != null
                is Message -> entity.text != null && entity.senderId != null && entity.id() != null && entity.state != null
                is User -> entity.name != null && entity.id() != null
                is GroupChat -> true
                else -> throw UnexpectedException("Unexpected entity type: ${entity.javaClass}")
            }
        }
    }
    data class ChatItem(val avatarUrl: String?, val lastMessage: String, val state: String) {
        override fun toString(): String {
            return "User with avatar url: $avatarUrl \n" +
                    "send message: \"$lastMessage\" \n" +
                    "with state: $state"
        }
    }

    private val entities: List<Entity> = Repository.getInfo().filter { e -> checkEntityCorrection(e) }
    private val userIdToUser: Map<Int, User> = entities.filterIsInstance<User>().stream()
            .collect( Collectors.toMap(User::id, Function.identity()) )
    private val messageIdToMessage: Map<Int, Message> = entities.filterIsInstance<Message>().stream()
            .collect( Collectors.toMap(Message::getId, Function.identity()) )

    fun getDataForUser(userId: Int, state: State? = null): List<ChatItem> {
        val userChats: List<ChatInterface> = entities.filterIsInstance<ChatInterface>()
                .filter { c -> c.getSenderIds().contains(userId) }.toList()
        val result: MutableList<ChatItem> = ArrayList()
        val user = userIdToUser[userId]!!

        for (chat in userChats) {
            val chatItem = chat.getMessageIds().map { id -> messageIdToMessage[id]!! }.toList()
                    .filter { message -> message.senderId == user.id && (state == null || message.state == state) }
                    .stream().min( Comparator.comparingLong { m -> m!!.timestamp } )
                    .map { message -> ChatItem(user.avatarUrl, message!!.text, stateToString(message.state)) }
            if (chatItem.isPresent) {
                result.add(chatItem.get())
            }
        }
        return result
    }

    private fun stateToString(state: State): String {
        return when (state) {
            is State.READ -> "Read"
            is State.UNREAD -> "Unread"
            is State.DELETED -> "Deleted by ${userIdToUser[state.userId]!!.name}"
        }
    }
}