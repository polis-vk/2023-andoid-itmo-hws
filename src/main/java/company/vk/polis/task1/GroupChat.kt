package company.vk.polis.task1

class GroupChat(var id: Int, var avatarUrl: String?, var userIds: List<User>, var messageIds: List<Int>) : Entity {

    override fun getId(): Int {
        return id
    }

    override fun isValid(): Boolean {
        return true
    }
}
