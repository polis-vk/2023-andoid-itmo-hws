package company.vk.polis.task1

fun main() {
    // я id тоже вывожу, так что ответ вроде верен
    val mesCon = MessageController();
    var list = mesCon.getItemsForUser(1)
    for (e in list) {
        println(e)
        println()
    }
    println("------------------------------------------------------")
    list = mesCon.getItemsForUser(0)
    for (e in list) {
        println(e)
        println()
    }
}