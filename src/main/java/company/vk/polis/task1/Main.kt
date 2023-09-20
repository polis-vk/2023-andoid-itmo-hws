package company.vk.polis.task1

fun main() {
    val controller = MessageController()
    controller.validData.filterIsInstance<User>().forEach {
        println(it)
    }
    println("--------------------")
    controller.getUserNameById(1).also { println(it) }
    controller.getUserMessagesCount(1).also { println(it) }
    controller.getUserChatItems(1).forEach {
        println("avatar: ${it.avatarUrl}")
        println("message: ${it.lastMessage}")
        println("state: ${it.state}")
    }
    println("--------------------")
    controller.getUserChatItems(2).forEach {
        println("avatar: ${it.avatarUrl}")
        println("message: ${it.lastMessage}")
        println("state: ${it.state}")
    }
    println("--------------------")
    controller.getUserChatItems(2, state = State.UNREAD()).forEach {
        println("avatar: ${it.avatarUrl}")
        println("message: ${it.lastMessage}")
        println("state: ${it.state}")
    }
}