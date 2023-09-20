package company.vk.polis.task1

class Message_Controller {
    private val entities: List<Entity> = Repository.getInfo().filter { it -> it.isValid };
    fun get_ChatItem(user_id: Int?, state: State_Msg?) :MutableList<ChatItem> {
        val chats: List<Chat> = entities.filterIsInstance<Chat>().filter { it -> (it.userIds.senderId == user_id || it.userIds.receiverId == user_id)};
        val user_chat_item: MutableList<ChatItem> = mutableListOf();
        var i: Int = 0;
        for(chat in chats) {
            val chat_messages_Ids: List<Int> = chat.messageIds;
            val messages: List<Message> = entities.filterIsInstance<Message>().filter { it -> (it.id in chat_messages_Ids && it.state == state)};
            val list_chat_msg: List<Message> = messages.sortedBy { it -> it.timestamp };
            val last_msg: String? = list_chat_msg[list_chat_msg.size - 1].text;
            val list_user_url: List<User> = entities.filterIsInstance<User>().filter { it -> it.id == user_id };
            val user_url: String? = list_user_url.get(0).avatarUrl;
            user_chat_item.add(++i, ChatItem(user_url, last_msg, state?.get_state(), user_id));
        }
        return user_chat_item;
    }
    fun count_sended_Messages(user_id: Int?) :Int {
        val chats: List<Chat> = entities.filterIsInstance<Chat>().filter { it -> it.userIds.receiverId == user_id};
        return chats.size;
    }
}