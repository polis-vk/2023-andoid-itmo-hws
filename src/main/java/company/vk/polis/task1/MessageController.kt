package company.vk.polis.task1

import java.lang.Exception
import java.util.Objects
import kotlin.reflect.typeOf

class MessageController {
    companion object {
        fun getValidInfo(): List<Entity> {
            return Repository.getInfo()?.filter {
                when (it) {
                    is Chat -> !listOf(it.id, it.userIds, it.messageIds).any { it == null } &&
                            !it.messageIds.any { it == null }
                    is Message -> !listOf(it.id, it.text, it.senderId, it.timestamp, it.state).any { it == null }
                    is User -> !listOf(it.id, it.name).any {it == null}
                    is GroupChat -> true // all members are NonNull except for avatarUrl
                    else -> false
                }
            } ?: emptyList()
        }

        internal fun getInfoByTypes(): InfoByTypes {
            val infoByTypes = InfoByTypes(HashMap(), HashMap(), HashMap(), HashMap())
            for (entity in getValidInfo()) {
                when (entity) {
                    is Chat -> infoByTypes.chats[entity.id] = entity
                    is Message -> infoByTypes.messages[entity.id] = entity
                    is User -> infoByTypes.users[entity.id] = entity
                    is GroupChat -> infoByTypes.groupChats[entity.id] = entity
                    else -> throw IllegalArgumentException() // unreachable
                }
            }
            return infoByTypes
        }

        internal fun getUserChatItems(userId: Int, state: State? = null): List<ChatItem> {
            val infoByTypes = getInfoByTypes()
            val chatItemList = mutableListOf<ChatItem>()
            // Maybe it's better to inherit Chat and GroupChat from AbstractChat
            for (chat in infoByTypes.chats.values) {
                val lastMessage = infoByTypes.messages[chat.messageIds.last()] // last() can be null
                val avatarUrl = infoByTypes.users[lastMessage?.senderId]?.avatarUrl
                if (state == null || lastMessage?.state?.javaClass == state.javaClass) {
                    val whoDeleted = infoByTypes.users[(lastMessage?.state as? State.DELETED)?.id]?.name
                    chatItemList.add(ChatItem(chat.id, avatarUrl, lastMessage, whoDeleted))
                }
            }
            for (chat in infoByTypes.groupChats.values) {
                val lastMessage = infoByTypes.messages[chat.messageIds.last()] // last() can be null
                val avatarUrl = infoByTypes.users[lastMessage?.senderId]?.avatarUrl
                if (state == null || lastMessage?.state?.javaClass == state.javaClass) {
                    val whoDeleted = infoByTypes.users[(lastMessage?.state as? State.DELETED)?.id]?.name
                    chatItemList.add(ChatItem(chat.id, avatarUrl, lastMessage, whoDeleted))
                }
            }
            return chatItemList
        }

        fun getUserMessageCount(userId: Int): Int =
            getValidInfo().count { (it as? Message)?.senderId == userId }

    }

    internal data class InfoByTypes(
        val chats: HashMap<Int, Chat>,
        val messages: HashMap<Int, Message>,
        val users: HashMap<Int, User>,
        val groupChats: HashMap<Int, GroupChat>
    )


    internal data class ChatItem(
        val chatId: Int,
        val avatarUrl: String?,
        val lastMessage: Message?,
        val deleteUserName: String? = null // for deleted messages
    ) {
        fun getMessageView(): String {
            return when {
                lastMessage == null -> ""
                // so weird because of space before nonnull name. Can't be written better
                lastMessage.state is State.DELETED -> "Сообщение было удалено" +
                        if (deleteUserName == null) "${lastMessage.senderId}" else " $deleteUserName"
                else -> lastMessage.text
            }
        }
    }
}