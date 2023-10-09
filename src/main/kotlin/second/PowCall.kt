package second

import java.util.concurrent.Callable
import kotlin.math.pow

class PowCall(private val num: Double, private val pow: Double) : Callable<Double> {
    override fun call(): Double {
        return num.pow(pow)
    }
}