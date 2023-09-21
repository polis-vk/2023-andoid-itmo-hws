package company.vk.polis.task1

interface ChatInterface {
    fun getMessageIds(): List<Int>
    fun getSenderId(): Int
}