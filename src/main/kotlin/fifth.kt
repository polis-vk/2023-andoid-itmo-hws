class SumCalculator(private val array: IntArray, private val start: Int, private val end: Int) : Runnable {
    var partialSum = 0

    override fun run() {
        for (i in start until end) {
            partialSum += array[i]
        }
    }
}

fun main() {
    val array = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val arraySize = array.size

    val task1 = SumCalculator(array, 0, arraySize / 2)
    val task2 = SumCalculator(array, arraySize / 2, arraySize)
    val thread1 = Thread(task1)
    val thread2 = Thread(task2)

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    val sum = task1.partialSum + task2.partialSum
    println("The sum of elements:: $sum")
}
