fun main(){
    val runnable = SumOfIntArray(arrayOf(1, 2, 3))
    Thread(runnable).start()
}

class SumOfIntArray(private val arr: Array<Int>) : Runnable {
    override fun run() {
        var s = 0
        for (a: Int in arr) s += a
        println(s)
    }
}