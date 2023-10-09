package fifth



fun main() {
    val arr = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val r1 = SumRun(arr.sliceArray(0 until arr.size / 2))
    val r2 = SumRun(arr.sliceArray(arr.size / 2 until arr.size))
    val t1 = Thread(r1)
    val t2 = Thread(r2)

    t1.start()
    t2.start()
    try {
        t1.join()
        t2.join()

        println(r1.sum + r2.sum)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}

class SumRun(private val array: Array<Int>) : Runnable {
    var sum = 0
    override fun run() {
        sum = array.sum()
    }
}