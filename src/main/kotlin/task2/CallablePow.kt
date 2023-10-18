package task2

import java.util.concurrent.Callable
import kotlin.math.pow

class CallablePow<T1 : Number, T2 : Number>(private val a: T1, private val b: T2) : Callable<Double> {
    override fun call(): Double {
        return a.toDouble().pow(b.toDouble())
    }
}
