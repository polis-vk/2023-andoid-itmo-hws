package task_1

fun main() {

    for (i in 1..6) {
        val array: Array<Int> = arrayOf(1, 2, 3, 4, 10).plus(i)
        val thread = Thread(MyRunnable(array))
        thread.start()
        thread.interrupt()
    }
}