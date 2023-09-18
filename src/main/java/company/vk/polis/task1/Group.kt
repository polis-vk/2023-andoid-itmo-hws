package company.vk.polis.task1

data class GroupChat(val id : Int, val Link_Avatar : String, var usersid : List<Int>, var messageIds : List<Int>) : Entity, AllChats {

    override fun getId(): Int {
        return id;
    }

    override fun Valid(): Boolean {
        if (id == null || Link_Avatar == null|| usersid == null || messageIds == null || id < 0){
            return false;
        }
        return true;
    }

    override fun getType() : Int{
        return 0;
    }

    fun ListMembers(): List<Int> {
        return usersid
    }

    override fun ChatId(): Int {
        return id
    }

}