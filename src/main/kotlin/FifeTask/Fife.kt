package FifeTask


fun main() {
    val arrayTask = arrayOf(12, 32, 21, -10, 24, 45, -40, 78, 37, -50)
    val run1 = RunCountWithRes(arrayTask.sliceArray(0..<arrayTask.size / 2))
    val run2 = RunCountWithRes(arrayTask.sliceArray(arrayTask.size / 2..<arrayTask.size))
    val thread1 = Thread(run1)
    val thread2 = Thread(run2)

    thread1.start()
    thread2.start()
    try {
        thread1.join()
        thread2.join()

        println(run1.getResult() + run2.getResult())
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}

class RunCountWithRes(private val array: Array<Int>) : Runnable {
    private var result: Int = 0

    fun getResult() : Int {
        return result
    }

    override fun run() {
        result = array.sum()
    }
}