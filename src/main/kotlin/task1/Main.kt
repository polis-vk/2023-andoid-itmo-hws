package task1

fun main() {
    val rSum = RunnableSum(intArrayOf(1, 2, 3, 4, 5))

    val thread = Thread(rSum)
    thread.start()
    try {
        thread.join()
    } catch (ignored: InterruptedException) {
    }
    println("End.")
}