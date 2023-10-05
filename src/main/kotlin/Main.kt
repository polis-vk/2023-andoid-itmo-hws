import java.lang.Runnable
import java.lang.Thread
import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.pow

fun main() {
    println("---------- Task 1 ----------")

    class ArraySum(private val ints: IntArray) : Runnable {
        override fun run() = println(ints.sum())
    }

    Thread(ArraySum(intArrayOf(1, 2, 3, 4, 5))).let {
        it.start()
        it.join()
    }

    println("---------- Task 2 ----------")

    class Power(private val n: Double, private val m: Double) : Callable<Double> {
        override fun call(): Double = n.pow(m)
    }

    val worker: ExecutorService = Executors.newSingleThreadExecutor()
    val result = worker.submit(Power(2.0, 3.0))
    println(result.get())
    worker.shutdownNow()
    worker.awaitTermination(5, TimeUnit.SECONDS);

    println("---------- Task 3 ----------")

    val f = ReentrantLock()
    val s = ReentrantLock()

    val a = Thread {
        f.lock()
        println("a acquired f")
        s.lock()
        println("a acquired s")
    }
    val b = Thread {
        s.lock()
        println("b acquired s")
        f.lock()
        println("b acquired f")
    }
//    listOf(a, b).onEach(Thread::start).forEach(Thread::join)

    println("---------- Task 4 ----------")

    val x = object {
        @Volatile
        var v = 0
    }

    val cd = CountDownLatch(3)

    (0..2).map {
        Thread {
            repeat(1000) { x.v++ }
            cd.countDown()
        }
    }.onEach(Thread::start).forEach(Thread::join)

    cd.await()
    println("Without synchronization ${x.v}")

    x.v = 0

    (0..2).map {
        Thread {
            repeat(1000) { synchronized(x) { x.v++ } }
            cd.countDown()
        }
    }.onEach(Thread::start).forEach(Thread::join)

    cd.await()
    println("With synchronization: ${x.v}")

    println("---------- Task 5 ----------")

    val array = intArrayOf(1, 2, 3, 4, 5, 6)
    val results = mutableListOf(0, 0)
    val partition = ceil(1.0 * array.size / results.size).toInt()

    (1 .. results.size).map { k ->
        class SplitArraySum(private val ints: IntArray) : Runnable {
            override fun run() {
                println(ints.toList().subList((k - 1) * partition, min(array.size, k * partition)).sum()
                    .also { results[k - 1] = it})
            }
        }

        Thread(SplitArraySum(array))
    }.onEach(Thread::start).forEach(Thread::join)

    println(results.sum())
}
