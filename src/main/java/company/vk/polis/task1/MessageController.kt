package company.vk.polis.task1

class MessageController {
    private val validInfo: List<Entity> = filterInfo();
    private val chatList: List<Chat> = getChatList(validInfo);
    private val groupChatList: List<GroupChat> = getGroupChatList(validInfo);
    private val messageList: List<Message> = getMessageList(validInfo);
    private val messageIdMap: Map<Int, Message> = getMessageIdMap(validInfo);
    private val userToUserIdMap: Map<Int, User> = getUserToUserId(validInfo);


    fun getValidInfo(): List<Entity> {
        return validInfo;
    }

    private fun filterInfo(): List<Entity> {
        var information: List<Entity> = Repository.getInfo();

        information = information.filterNotNull().filter { entity ->
            entity.id != null &&
                    when (entity) {
                        is Chat -> !(entity.messageIds == null || entity.userIds == null)
                        is Message -> !(entity.senderId == null
                                || entity.text == null || entity.timestamp == null)
                        is User -> entity.name != null
                        is GroupChat -> !(entity.messageIds == null || entity.userIds == null)
                        else -> false
                    }
        }

        return information;
    }

    private fun getMessageIdMap(validInfo: List<Entity>): Map<Int, Message> {
        val messagesInfo: List<Message> = validInfo.filterIsInstance<Message>()
        return messagesInfo.associateBy({ it.id() }, { it })
    }

    private fun getUserToUserId(validInfo: List<Entity>): Map<Int, User> {
        val messagesInfo: List<User> = validInfo.filterIsInstance<User>()
        return messagesInfo.associateBy({ it.id() }, { it })
    }

    private fun getChatList(validInfo: List<Entity>): List<Chat> {
        return validInfo.filterIsInstance<Chat>();
    }

    private fun getGroupChatList(validInfo: List<Entity>): List<GroupChat> {
        return validInfo.filterIsInstance<GroupChat>();
    }

    private fun getMessageList(validInfo: List<Entity>): List<Message> {
        return validInfo.filterIsInstance<Message>();
    }


    data class ChatItem(val avatarURL: String?,
                        var lastMessage: String,
                        val messageState: State) {
    }


    fun getChatItems(userId: Int, messageState: State = State.NOTHING): List<ChatItem> {
        val chatItemList: MutableList<ChatItem> = mutableListOf();

        val receiverChats: List<Chat> = chatList.filter { chat -> chat.userIds.receiverId == userId };


        for (chat in receiverChats) {
            var lastMessage: Message? = messageIdMap[chat.messageIds[0]]
            for (messageId in chat.messageIds) {
                if (messageIdMap[messageId]!!.timestamp > lastMessage!!.timestamp) {
                    lastMessage = messageIdMap[messageId]
                }
            }

            val chatItem: ChatItem = ChatItem(userToUserIdMap[chat.userIds.senderId]?.avatarUrl, lastMessage!!.text, lastMessage.state);
            chatItemList.add(chatItem);
        }

        val receiverGroupChats: List<GroupChat> = groupChatList.filter { chat -> chat.userIds!!.contains(userId)};
        for (groupChat in receiverGroupChats) {
            var lastMessage: Message? = messageIdMap[groupChat.messageIds!![0]]
            for (messageId in groupChat.messageIds) {
                if (messageIdMap[messageId]!!.timestamp > lastMessage!!.timestamp) {
                    lastMessage = messageIdMap[messageId]
                }
            }

            val chatItem: ChatItem = ChatItem(groupChat.avatarUrl, lastMessage!!.text, lastMessage.state);
            chatItemList.add(chatItem);
        }

        if (messageState != State.NOTHING) {
            return chatItemList.filter { chatItem -> chatItem.messageState == messageState };
        } else {
            return chatItemList;
        }

    }

    fun getCountOfMessage(userId: Int): Int {
        return messageList.count { message -> message.senderId == userId };
    }
}



