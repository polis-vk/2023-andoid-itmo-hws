package task_5

class MyRunnable(private val array: Array<Int>) : Runnable {

    private var result  : Int = 0

    override fun run() {
        this.result = array.sum()
    }


    fun getResult(): Int {
        return result
    }
}