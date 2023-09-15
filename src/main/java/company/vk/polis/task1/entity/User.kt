package company.vk.polis.task1.entity

data class User(override val id: Int, val name: String, val avatarUrl: String?) : Entity {
    var lastMessage: Message? = null
}
