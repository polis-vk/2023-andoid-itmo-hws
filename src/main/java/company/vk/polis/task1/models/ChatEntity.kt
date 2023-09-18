package company.vk.polis.task1.models

interface ChatEntity : Entity {
    fun checkUser(userId: Int) : Boolean
    fun messageIds() : List<Int>
}