package task5

class SumArray(private val array: Array<Int>) : Runnable {
    private var sum = 0
    override fun run() {
        sum = array.sum()
    }
    fun getSum(): Int {
        return sum
    }
}

fun main() {
    val array = readln().split(" ").map { it.toInt() }.toTypedArray()
    val sum1 = SumArray(array.sliceArray(0 until array.size / 2))
    val sum2 = SumArray(array.sliceArray(array.size / 2 until array.size))
    val thread1 = Thread(sum1)
    val thread2 = Thread(sum2)
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    println(sum1.getSum() + sum2.getSum())
}