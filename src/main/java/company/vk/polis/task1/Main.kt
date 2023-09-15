package company.vk.polis.task1

import company.vk.polis.task1.controller.UserController
import company.vk.polis.task1.service.currentInfo

fun main() {
    val alexsin = UserController.register("AlexSin")
    val testUser = UserController.register("testUser")

    val chat = UserController.createChat(alexsin, testUser, "best-friends")

    val helloMessage = UserController.write(alexsin, chat, "Hello, my friend!")


    UserController.read(testUser, helloMessage)
    UserController.deleteMessage(testUser, helloMessage)

    println(currentInfo().joinToString("\n"))
    println("  alexsin last: ${alexsin.lastMessage}")
    println(" testUser last: ${testUser.lastMessage}")
    println("helloMsg state: ${helloMessage.state}")
}
