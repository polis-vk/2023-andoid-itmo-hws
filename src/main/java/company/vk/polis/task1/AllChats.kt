package company.vk.polis.task1

interface AllChats : Entity{
    fun getListMessagesId(): List<Int>
    fun getListUsersId(): List<Int>

    fun getAvatar(): String
}