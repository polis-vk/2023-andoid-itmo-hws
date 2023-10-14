package task_2

import java.util.concurrent.Executors

fun main() {
    val executor = Executors.newSingleThreadExecutor()
    val callable = MyCallable(100, 2.1)
    val future = executor.submit(callable)
    val result = future.get()
    println(result)
    executor.shutdown()

}