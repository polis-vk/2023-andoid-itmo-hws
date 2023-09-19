package company.vk.polis.task1

interface MetaChat : Entity {
    fun getUserIds(): List<Int>?
    fun getMessageIds(): List<Int>?
}