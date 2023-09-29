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

    override fun checkValid(): Boolean {
        if (id == null || Link_Avatar == null|| usersid == null || messageIds == null || id < 0){
            return false;
        }
        return true;
    }

    override fun getAvatar(): String {
        return Link_Avatar
    }

}