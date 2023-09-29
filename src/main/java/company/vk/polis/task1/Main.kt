package company.vk.polis.task1

import company.vk.polis.task1.MessageController

fun main() {
    //TODO()

    val messageController = MessageController()
    val info = messageController.validInfo
    for (entity in info){
        println(entity)
    }

    println(messageController.getCountOfMessage(1))
    println(messageController.getCountOfMessage(2))
    println(messageController.getCountOfMessage(3))
    println(messageController.getCountOfMessage(4))

    println(messageController.getChatItems(1,  State.Deleted(1)))
    println(messageController.getChatItems(1,  State.Deleted(2)))
    println(messageController.getChatItems(4,  State.Deleted(3)))
    println(messageController.getChatItems(5,  State.Deleted(1)))
}