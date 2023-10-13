import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.math.pow

fun main(){
    val executorService: ExecutorService = Executors.newFixedThreadPool(1)

    val base = 2.0
    val exponent = 3
    val callableImpl = CallableImpl(base, exponent)

    val future: Future<Double> = executorService.submit(callableImpl)

    try {
        val result = future.get()
        println("Результат возведения $base в степень $exponent: $result")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    executorService.shutdown()
}

class CallableImpl(private val base: Double, private val exponent: Int) : Callable<Double> {
    override fun call(): Double {
        return base.pow(exponent.toDouble())
    }
}