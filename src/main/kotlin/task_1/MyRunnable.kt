package task_1

class MyRunnable(private val array : Array<Int>) : Runnable {
    override fun run() {
        val result = array.sum()
        println(result)
    }


}