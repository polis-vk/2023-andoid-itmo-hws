package company.vk.polis.task1

data class GroupChat(val id : Int, val Link_Avatar : String, var usersid : List<Int>, var messageIds : List<Int>) : AllChats {

    override fun getId(): Int {
        return id;
    }

    override fun getListMessagesId(): List<Int> {
        return messageIds;
    }

    override fun getListUsersId(): List<Int> {
        return usersid;
    }


    override fun getAvatar(): String {
        return Link_Avatar
    }

}