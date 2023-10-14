package task_2

import java.util.concurrent.Callable
import kotlin.math.pow

class MyCallable(private val number : Number, private val power : Number) : Callable<Number> {
    override fun call(): Number {
        return (number.toDouble()).pow(power.toDouble())
    }
}