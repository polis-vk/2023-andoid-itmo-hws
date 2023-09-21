package company.vk.polis.task1.ui

import company.vk.polis.task1.controller.MessageController

fun main() {
    val controller = MessageController()
    (0..9).forEach {
        println("User $it")
        println(controller.getMessagesToUser(it))
        println(controller.getUnreadMessagesForUser(it))
        controller.chatIds(it).forEach { chatId ->
            println("Uread messages for chatId $chatId: ${controller.getUnreadMessagesForChat(it, chatId)}")
        }
        println()
    }
}