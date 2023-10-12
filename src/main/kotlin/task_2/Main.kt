package task_2

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.math.pow

class Power(private val base: Int, private val exponent: Int) : Callable<Int> {
    override fun call(): Int {
        return base.toDouble().pow(exponent.toDouble()).toInt()
    }
}

fun main(args: Array<String>) {
    println("task 2")
    val executorService = Executors.newFixedThreadPool(1)
    val powerCalculator = Power(2, 3)
    val future: Future<Int> = executorService.submit(powerCalculator)
    val result = future.get()
    println("Результат возведения в степень: $result")
    executorService.shutdown()
}
