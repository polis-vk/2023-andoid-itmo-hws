package second

import java.util.concurrent.Executors

fun main() {
    val executor = Executors.newSingleThreadExecutor()
    val future = executor.submit(PowCall(4.0, 4.0))

    println(future.get())
    executor.shutdown()
}