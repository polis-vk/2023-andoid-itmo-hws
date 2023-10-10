package task1

fun main(args: Array<String>) {
    val array = arrayOf(1, 2, 3, 4, 5)
    val thread = Thread(ArraySum(array))
    thread.start()
}