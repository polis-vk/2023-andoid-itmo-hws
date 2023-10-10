package task1

class ArraySum(private val arr: Array<Int>) : Runnable {
    override fun run() {
        var sum = 0
        for (e in arr) {
            sum += e
        }
        println("Sum of array ${arr.contentToString()} is $sum")
    }
}