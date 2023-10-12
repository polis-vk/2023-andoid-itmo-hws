package task1

class SumArray(private val array: Array<Int>) : Runnable {
    override fun run() {
        println(array.sum())
    }
}

fun main() {
    val array = readln().split(" ").map { it.toInt() }.toTypedArray()
    Thread(SumArray(array)).start()
}