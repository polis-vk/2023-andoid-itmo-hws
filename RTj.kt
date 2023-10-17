fun main(){
    val arr = arrayOf(1,2,3,4,5,6,7);
    val r1 = SumOfPartIntArray(arr, 0, arr.size / 2)
    val r2 = SumOfPartIntArray(arr, arr.size / 2, arr.size)
    val t1 = Thread(r1)
    val t2 = Thread(r2)
    t1.start()
    t2.start()
    t1.join()
    t2.join()
    println(r1.getResult() + r2.getResult())
}

class SumOfPartIntArray(private val arr: Array<Int>, private val l: Int, private val r: Int) : Runnable {
    private var result: Int = 0
    fun getResult() = result
    override fun run() {
        result = 0
        for (i: Int in l..<r) result += arr[i]
    }
}