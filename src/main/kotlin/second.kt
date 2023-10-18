import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.pow

class PowCalculator(private val base: Double, private val exponent: Double) : Callable<Double> {
    override fun call(): Double {
        return base.pow(exponent)
    }
}

fun main() {
    val base = 11.0
    val exponent = 2.0
    val executor = Executors.newSingleThreadExecutor()
    val calculator = PowCalculator(base, exponent)
    val futureResult = executor.submit(calculator)
    val result = futureResult.get()

    println(result)

    executor.shutdown()
}
