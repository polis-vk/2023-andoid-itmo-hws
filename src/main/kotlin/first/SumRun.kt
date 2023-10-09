package first

class SumRun(private val array: Array<Int>) : Runnable {
    override fun run() {
        println(array.sum())
    }
}