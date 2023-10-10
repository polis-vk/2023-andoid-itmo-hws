package `3task`

import java.util.concurrent.Executors
import kotlin.math.pow
import kotlin.random.Random
import java.util.concurrent.Callable

fun main(args: Array<String>) {
    val threadA = Thread {
        A.methodA()
    }
    val threadB = Thread {
        B.methodB()
    }
    threadA.start()
    threadB.start()
}

object A{
    @Synchronized
    fun methodA() {
        print(Thread.currentThread())
        println(" in methodA")
        Thread.sleep(1000L)
        B.methodB()
        println("Success")
    }
}

object B{
    @Synchronized
    fun methodB() {
        print(Thread.currentThread())
        println(" in methodB")
        Thread.sleep(1000L)
        A.methodA()
        println("Success")
    }
}