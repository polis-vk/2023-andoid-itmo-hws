package task2

import java.util.concurrent.Executors

fun main() {
    val executor = Executors.newSingleThreadExecutor()
    val callableTask = PowClass(5.0, 6.0)
    val future = executor.submit(callableTask)
    val result = future.get()
    println("Результат: $result")
    executor.shutdown()
}
