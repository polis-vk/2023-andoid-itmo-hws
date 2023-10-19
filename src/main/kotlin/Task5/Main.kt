package Task5

//Runnable, Thread, join() *

import kotlin.system.measureTimeMillis

fun main(args: Array<String>){
    val size = 10
    val array = Array<Int>(size) {item -> (0..100).random()}
    val time = measureTimeMillis {
        val sum1 = SumRun( array.sliceArray(0 until array.size /2) )
        val sum2 = SumRun( array.sliceArray(array.size / 2 until array.size))
        val thread1 = Thread(sum1)
        val thread2 = Thread(sum2)
        thread1.start()
        thread2.start()
        thread1.join()
        thread2.join()
        println(sum1.sum + sum2.sum)
    }
    println("Time in ms with 2 threads = $time")
}

class SumRun (private val array: Array<Int>): Runnable {
    var sum = 0
    override fun run() {
        sum = array.sum()
    }
}