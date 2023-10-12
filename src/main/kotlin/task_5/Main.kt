package task_5

class Sum(private val array: IntArray, private val start: Int, private val end: Int) : Runnable {
    var result = 0
    override fun run() {
        for (i in start until end) {
            result += array[i]
        }
    }
}

fun main(args: Array<String>) {
    println("task 5")
    val numbers: IntArray = intArrayOf(1, 3, 5, 7, 9, 11)
    val middle = numbers.size / 2

    val sum1 = Sum(numbers, 0, middle)
    val sum2 = Sum(numbers, middle, numbers.size)

    val thread1 = Thread(sum1)
    val thread2 = Thread(sum2)

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    val totalSum = sum1.result + sum2.result
    println("сумма: $totalSum")
}
