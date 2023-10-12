package task2

import java.util.concurrent.Callable
import java.util.concurrent.Executors

class Pow(private val a: Int, private val k: Int) : Callable<Long> {
    override fun call(): Long {
        return pow(k)
    }

    private fun pow(k: Int) : Long =
        if (k == 0) {
            1
        } else if (k % 2 == 1) {
            a.toLong() * pow(k - 1)
        } else {
            val res = pow(k / 2)
            res * res
        }
}

fun main() {
    val (a, k) = readln().split(" ").map { it.toInt() }
    val executor = Executors.newSingleThreadExecutor()
    val future = executor.submit(Pow(a, k))
    executor.shutdown()
    println(future.get())
}