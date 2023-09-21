package company.vk.polis.task1

import company.vk.polis.task1.MessageController

fun main() {
    //TODO()

    val messageController = MessageController()

    val info = messageController.getValidInfo()

    for (entity in info){
        println(entity)
    }



}