package company.vk.polis.task1

fun main() {
    val controller = MessageController()
    (0..9).forEach {
        println("User $it")
        println(controller.getMessagesToUser(it))
        println(controller.getMessagesCountFromUser(it))
        println(controller.getMessagesCountFromUser(it, 1))
    }
}