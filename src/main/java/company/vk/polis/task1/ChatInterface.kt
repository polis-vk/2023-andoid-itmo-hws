package company.vk.polis.task1

interface ChatInterface : Entity{
    fun getChatUserIds(): List<Int?>?
    fun getChatMessageIds(): List<Int?>?
}