class ArraySumCalculator(private val array: IntArray) : Runnable {
    override fun run() {
        println("The sum of elements: ${array.sum()}")
    }
}

fun main() {
    val array = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val calculator = ArraySumCalculator(array)
    val thread = Thread(calculator)

    thread.start()
    thread.join()
    println("All threads have completed execution")
}
