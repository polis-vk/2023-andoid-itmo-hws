package fourth

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

fun main() {
    val executor1 = Executors.newFixedThreadPool(3)
    var num1 = 0

    val latch1 = CountDownLatch(3)
    for (t in 0 until 3) {
        executor1.submit {
            for (i in 0..999) {
                num1++
            }
            latch1.countDown()
        }
    }
    latch1.await()
    println("Without synchronised $num1")

    // ------------------

    val monitor = Any()
    val executor2 = Executors.newFixedThreadPool(3)
    var num2 = 0

    val latch2 = CountDownLatch(3)
    for (t in 0 until 3) {
        executor2.submit {
            for (i in 0..999) {
                synchronized(monitor) {
                    num2++
                }
            }
            latch2.countDown()
        }
    }
    latch2.await()
    println("With synchronised $num2")
}