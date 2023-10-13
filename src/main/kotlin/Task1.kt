
fun main(){
    val ints = intArrayOf(1,2,3,4,5,6)
    val runnableImpl = RunnableImpl(ints)
    val thread = Thread(runnableImpl)
    thread.start()
}
class RunnableImpl(private val array: IntArray) : Runnable {
    override fun run() {
        val sum = array.sum()
        println("Сумма элементов массива: $sum")
    }
}

