package task_4_1

fun main() {
    val array: IntArray = intArrayOf(1, 2, 3, 4, 5)
    val thred = Thread(RunnableDanteRunnable(array))
    thred.start()
}