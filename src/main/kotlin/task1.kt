class Task1(private val arr: Array<Int>): Runnable {
    override fun run() {
        var sum: Long = 0
        for (element in arr) {
            sum += element
        }
        println(sum)
    }

}

fun main() {
    val run = Task1(arrayOf(1, 2, 3, 4, 5))
    val thread = Thread(run)
    thread.start()
}
