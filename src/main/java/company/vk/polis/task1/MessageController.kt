package company.vk.polis.task1

import company.vk.polis.task1.models.*
import java.util.ArrayList

class MessageController {

    fun getFilterEntity(): List<Entity> = Repository.getInfo().stream().filter { e -> e.isValid }.toList();

    fun getChartItemFotUser(userId: Int, state: StateType = StateType.DEFAULT): List<ChatItem> {
        // prepare data
        val entities = getFilterEntity()
        val messages = entities.filterIsInstance<Message>()
        val chatsForUser : List<ChatEntity> = entities.stream().filter { e ->
            (e is Chat && (e.userIds.receiverId == userId)) || e is GroupChat && e.userIds.contains(userId)
        }.toList() as List<ChatEntity>

        val result = ArrayList<ChatItem>()
        if (state.equals(StateType.DEFAULT)) {
            for (chat in chatsForUser) {
                if (chat.checkUser(userId)) {
                    val messagesForChat = messages.stream().filter{m -> chat.getMessageIds().contains(m.id)}.toList()
                    when (chat) {
                        is Chat -> result.addAll(messagesForChat.stream().map { m ->
                            val user = getUserForId(entities, userId)
                            return@map ChatItem(m, m.state, user.avatarUrl) }.toList())
                        is GroupChat -> result.addAll(messagesForChat.stream().map { m ->
                            return@map ChatItem(m, m.state, chat.uri) }.toList())
                    }
                }
            }
        } else {
            for (chat in chatsForUser) {
                if (chat.checkUser(userId)) {
                    val message = messages.stream().filter{m -> chat.getMessageIds().contains(m.id)}.sorted().findFirst().get()
                    when (chat) {
                        is Chat -> result.add(
                            ChatItem(message, message.state, getUserForId(entities, chat.userIds.receiverId).avatarUrl))
                        is GroupChat -> result.add(ChatItem(message, message.state, chat.uri))
                    }
                }
            }
        }
        return result
    }

    fun getCountMessageForUser(userId: Int): Int {
        //prepare data
        val entities = getFilterEntity()
        val messages: List<Message> = entities.filterIsInstance<Message>()
        val chatsForUser: List<ChatEntity> = entities.stream()
            .filter { e -> e is ChatEntity && e.checkUser(userId)}.toList() as List<ChatEntity>

        return chatsForUser.stream().map { ch -> messages.stream().filter {
                m -> ch.getMessageIds().contains(m.id()) && (!StateType.DELETED.equals(m.state)) }.count()
        }.count().toInt()
    }

    private fun getUserForId(entities: List<Entity>, userId: Int): User {
        val users: List<User> =
            entities.stream().filter { e -> (e is User) && (e.id() == userId) }.toList() as List<User>
        if (users.isEmpty())
            throw IllegalArgumentException("User not found")
        return users.get(0)
    }
}