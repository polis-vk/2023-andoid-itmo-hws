package task2

import java.util.concurrent.Executors

fun main(args: Array<String>) {
    val a = 2
    val n = 55
    val executor = Executors.newSingleThreadExecutor()
    val future = executor.submit(Pow(a, n))
    println("$a^$n = ${future.get()}")
    executor.shutdown()
}