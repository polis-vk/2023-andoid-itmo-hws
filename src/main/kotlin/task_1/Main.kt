package task_1

import java.util.concurrent.Executors
import java.util.concurrent.Future

class Sum(private val array: IntArray) : Runnable {
    var result = 0
    override fun run() {
        result = array.sum()
    }
}

fun main(args: Array<String>) {
    println("task 1")
    val numbers: IntArray = intArrayOf(1, 3, 5, 7, 9)
    val sum = Sum(numbers)
    val thread = Thread(sum)
    thread.start()

    thread.join()

    val result = sum.result
    println("Сумма элементов массива: $result")
}
