package company.vk.polis.task1

internal data class GroupChat(val id: Int?, val avatarUrl : String?, val users : List<User>?, val messageIds: List<Int>): Entity {
    override fun getId(): Int? {
        return id
    }
}