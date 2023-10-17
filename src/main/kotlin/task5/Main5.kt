package task5

fun main() {
    val arr: Array<Int> = arrayOf(1, 2, 4, 12, 243)
    val runnableThread1 = SumClass(arr.take(if (arr.size % 2 == 0) arr.size / 2 else arr.size / 2 + 1))
    val runnableThread2 = SumClass(arr.takeLast(arr.size / 2))
    val thread1 = Thread(runnableThread1)
    val thread2 = Thread(runnableThread2)

    thread1.start()
    thread2.start()

    try {
        thread1.join()
        thread2.join()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    println("First: " + runnableThread1.getSum())
    println("Second: " + runnableThread2.getSum())
    println("Together: " + (runnableThread1.getSum() + runnableThread2.getSum()))
}
