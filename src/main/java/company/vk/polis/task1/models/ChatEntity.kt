package company.vk.polis.task1.models

interface ChatEntity : Entity {
    fun checkUser(userId: Int) : Boolean
    fun getMessageIds() : List<Int>
}