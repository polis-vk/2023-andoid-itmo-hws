package Task2
//Callable

import java.util.concurrent.Callable
import kotlin.math.pow
import kotlin.random.Random

fun main(args: Array<String>){
    val executor = java.util.concurrent.Executors.newSingleThreadExecutor()
    //Don't know why but i can't call it from Executors.*** with import java.util.concurrent.Executors//
    val number = Random.nextDouble()
    val pow = Random.nextDouble()
    val future = executor.submit( CallPow(number, pow) )
    println( future.get() )
    executor.shutdown()
}

class CallPow(private val number: Double, private val pow: Double) : Callable<Double> {
    override fun call(): Double {
        return number.pow(pow)
    }
}

