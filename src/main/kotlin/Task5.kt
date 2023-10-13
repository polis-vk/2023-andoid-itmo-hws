


fun main() {
    val intArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var resultSum = 0
    val locker = Any()
    val runnableImpl1 = RunnableImp(intArray, 0, 4, locker) { resultSum += it }
    val runnableImpl2 = RunnableImp(intArray, 5, 9, locker) { resultSum += it }
    val thread1 = Thread(runnableImpl1)
    val thread2 = Thread(runnableImpl2)
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    println("Сумма элементов массива: $resultSum")
}

class RunnableImp(
    private val intArray: IntArray,
    private val l: Int,
    private val r: Int,
    private val locker: Any,
    private val onResult: (Int) -> Unit,
) : Runnable {
    override fun run() {
        var sum = 0
        for (i in l..r) {
            sum += intArray[i]
        }
        synchronized(locker) {
            onResult(sum)
        }
    }
}