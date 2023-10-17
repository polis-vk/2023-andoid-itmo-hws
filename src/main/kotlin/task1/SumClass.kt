package task1

class SumClass(private val args: Array<Int>) : Runnable {
    override fun run() {
        var sum = 0
        for (number: Int in args) {
            sum += number
        }
        println(sum)
    }
}
