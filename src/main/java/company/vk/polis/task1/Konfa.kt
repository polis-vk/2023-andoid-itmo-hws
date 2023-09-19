package company.vk.polis.task1

class Konfa (val id : Int, val avUrl: String?, val membersIds: List<Int>, val messagesIds: List<Int>) : Entity, Chatable {
    override fun getMessages(): List<Int> {
        return messagesIds;
    }

    override fun getUsers(): List<Int> {
        return membersIds;
    }

    override fun getId(): Int {
        return id;
    }
}