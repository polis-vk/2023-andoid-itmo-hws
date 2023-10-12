package task_4_2

import java.util.concurrent.Callable
import kotlin.math.*

class INeedMorePower(val base: Double, val power: Int) : Callable<Double> {
    override fun call(): Double {
        return base.pow(power);
    }
}