import kotlin.Result

class SharedResource(private val n: Int) {
    @Volatile
    var left = 0
    @Volatile
    var right: Int = n - 1
}

object SumResult {
    var firstResult: Long = 0
    var secondResult: Long = 0
}


class Task5(private val arr: Array<Int>, private val reversed: Boolean, private var resource: SharedResource): Runnable {
    override fun run() {
        var localResult: Long = 0;
        if (!reversed) {
            while (resource.left < resource.right) {
                localResult += arr[resource.left]
                resource.left++
            }
            SumResult.firstResult = localResult
        } else {
            while (resource.left <= resource.right) {
                localResult += arr[resource.right]
                resource.right--
            }
            SumResult.secondResult = localResult
        }
    }

}

const val N = 10_000_000

fun main() {
    var resource = SharedResource(N)

    println("Generating array...")
    val array = Array(N) { i -> i % 5 } // {0, 1, 2, 3, 4, 0, 1, 2, 3, 4, ..., 0, 1, 2, 3, 4}
    println("Array generated.")

    val run1 = Task5(array, false, resource)
    val run2 = Task5(array, true, resource)
    val th1 = Thread(run1)
    val th2 = Thread(run2)
    th1.start()
    th2.start()

    println("Running calculations...")
    try {
        th1.join()
        th2.join()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    println("Calculations finished.")
    println("Left value: ${SumResult.firstResult}")
    println("Right value: ${SumResult.secondResult}")
    println("Overall value: ${SumResult.firstResult + SumResult.secondResult}")
    println("Correct value: ${2 * N}")
    println("Left index stopped at ${resource.left}. Right index stopped at ${resource.right}")

}