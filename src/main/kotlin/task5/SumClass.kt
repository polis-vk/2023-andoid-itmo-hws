package task5

class SumClass(private val args: List<Int>) : Runnable {
    private var sum = 0

    override fun run() {
        for (number: Int in args) {
            sum += number
        }
    }

    fun getSum(): Int {
        return sum
    }
}
