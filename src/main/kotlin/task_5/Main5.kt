package task_5

fun main() {

    val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val runnable1 = MyRunnable(array.sliceArray(0..3))
    val runnable2 = MyRunnable(array.sliceArray(4..7))

    val thread1 = Thread(runnable1)
    val thread2 = Thread(runnable2)

    thread1.start()
    thread2.start()

    try {
        thread1.join()
        thread2.join()
    } catch (e : InterruptedException) {
        // nothing
    }

    println("Result is ${runnable1.getResult() + runnable2.getResult()}")

}