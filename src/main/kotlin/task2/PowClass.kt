package task2

import java.util.concurrent.Callable
import kotlin.math.pow

class PowClass(private val number: Double, private val power: Double) : Callable<Double> {
    override fun call(): Double {
        return number.pow(power)
    }
}
