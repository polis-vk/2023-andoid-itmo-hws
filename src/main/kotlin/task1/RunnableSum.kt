package task1

class RunnableSum(private var array: IntArray) : Runnable {
    override fun run() {
        println("The sum of the elements of the array: ${array.sum()}")
    }
}