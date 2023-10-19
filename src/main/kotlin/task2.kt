import java.util.concurrent.Callable
import java.util.concurrent.Executors

class Task2(private val base: Int, private val degree: Int): Callable<Long> {
    private fun internal(a: Int, n: Int): Long {
        if (n == 0) {
            return 1L
        }
        if (n % 2 == 0) {
            val ret = internal(a, n / 2)
            return ret * ret
        }
        return internal(a, n - 1) * a
    }

    override fun call(): Long {
        return internal(base, degree)
    }

}

fun main() {
    val executor = Executors.newSingleThreadExecutor()
    val task = Task2(2, 11)
    val future = executor.submit(task)
    val result = future.get()
    println(result)
    executor.shutdown()
}