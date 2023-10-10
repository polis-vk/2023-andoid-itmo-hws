package task5

class ArraySum(private val arr: Array<Int>) : Runnable {
    private var sum: Long = 0
    fun getSum(): Long {
        return sum
    }
    override fun run() {
        sum = 0
        for (e in arr) {
            sum += e
        }
    }
}