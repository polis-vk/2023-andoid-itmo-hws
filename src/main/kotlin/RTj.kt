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
    println(r1.result + r2.result)
}

class SumOfPartIntArray(private val arr: Array<Int>, private val l: Int, private val r: Int) : Runnable {
    var result: Int = 0
        private set
    override fun run() {
        result = 0
        for (i: Int in l..<r) result += arr[i]
    }
}