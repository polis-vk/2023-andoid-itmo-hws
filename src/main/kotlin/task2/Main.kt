package task2

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

fun main() {
    val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val future: Future<Double> = executorService.submit(CallablePow(2, 3))

    try {
        println("Result pow: ${future.get()}")
    } catch (e: Exception) {
        e.printStackTrace()
    }

    shutdownAndAwaitTermination(executorService)
}


/** @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html">Official docs</a>
 */
fun shutdownAndAwaitTermination(pool: ExecutorService) {
    pool.shutdown() // Disable new tasks from being submitted
    try {
        // Wait a while for existing tasks to terminate
        if (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
            pool.shutdownNow() // Cancel currently executing tasks
            // Wait a while for tasks to respond to being cancelled
            if (!pool.awaitTermination(1, TimeUnit.SECONDS)) System.err.println("Pool did not terminate")
        }
    } catch (ex: InterruptedException) {
        // (Re-)Cancel if current thread also interrupted
        pool.shutdownNow()
        // Preserve interrupt status
        Thread.currentThread().interrupt()
    }
}