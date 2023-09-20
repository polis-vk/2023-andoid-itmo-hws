package company.vk.polis.task1

import org.jetbrains.annotations.Nullable
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

object MessageController {
    fun filterNotNull(entities: List<Entity>) = entities.filterNot { e ->
        e::class.memberProperties.all {
            !it.hasAnnotation<Nullable>() &&
            it.getter.run {
                isAccessible = true
                call(e) == null
            }
        }
    }

    fun valid() = filterNotNull(Repository.getInfo())

    data class ChatItem(val avatarUrl: String?, val lastMessageId: Int, val state: State)

    private inline fun <reified E : Entity> find(id: Int) = valid().filterIsInstance<E>().find { it.id == id }

    fun findMessage(userId: Int, state: State?): List<ChatItem> = valid().mapNotNull {
        when (it) {
            is Conversation -> find<Message>(it.messageIds().last())?.let { message ->
                when {
                    state == null || message.state == state -> {
                        find<User>(message.senderId)?.let { user ->
                            if (state is DELETED) {
                                println("Сообщение было удалено пользователем ${find<User>(state.userId)?.name}")
                            }
                            ChatItem(user.avatarUrl, message.id, message.state)
                        }
                    }
                    else -> null
                }
            }
            else -> null
        }
    }

    fun countMessages(userId: Int) = Repository.getInfo().filterIsInstance<Message>().count { it.senderId == userId }
}
