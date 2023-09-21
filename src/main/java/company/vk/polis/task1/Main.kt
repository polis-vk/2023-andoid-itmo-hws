package company.vk.polis.task1

fun main() {
    val mc = MessageController()
    mc.chatItems(1).forEach {
        println(it)
    }
}