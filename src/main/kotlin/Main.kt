import java.lang.Runnable
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.pow



fun main(args: Array<String>) {
    println("Hello threads")

    announceTask(1)
    val arr = intArrayOf(1, 2, 3, 4, 5)
    val thread = Thread(FirstTask(arr))
    thread.start()

    try {
        thread.join()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    announceTask(2)
    val executor = Executors.newSingleThreadExecutor()
    val future = executor.submit(SecondTask(2.0, 10.0))

    try {
        println("2.0 pow 10.0 = ${future.get()}")
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } finally {
        executor.shutdown()
    }

    announceTask(3)
    val locks = Array(2) { ReentrantLock() }
    val thread1 = Thread {
        locks[0].withLock {
            Thread.sleep(200)
            locks[1].withLock {
                println("first thread")
            }
        }
    }

    val thread2 = Thread {
        locks[1].withLock {
            Thread.sleep(200)
            locks[0].withLock {
                println("second thread")
            }
        }
    }

    thread1.start()
    thread2.start()

    try {
        thread1.join(2000)
        thread2.join(2000)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } finally {
        println("first thread is waiting: ${thread1.isAlive}")
        println("second thread is waiting: ${thread2.isAlive}")
    }

    announceTask(4)
    var counter = 0
    val notSynchronizedThreads = Array(3) {
        Thread {
            repeat(10000) { counter++ }
        }
    }

    notSynchronizedThreads.forEach { it.start() }
    try {
        notSynchronizedThreads.forEach { it.join() }
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    println("Without synchronization: $counter")

    val synchronizationObject = object {}
    val synchronizedThreads = Array(3) {
        Thread {
            repeat(10000) {
                synchronized(synchronizationObject) { counter++ }
            }
        }
    }

    counter = 0
    synchronizedThreads.forEach { it.start() }
    try {
        synchronizedThreads.forEach { it.join() }
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    println("With synchronization: $counter")

    announceTask(5)
    val array = IntArray(10000) { it }
    val firstHalfWorker = FifthTask(array.sliceArray(0 until array.size / 2))
    val secondHalfWorker = FifthTask(array.sliceArray(array.size / 2 until array.size))

    val threads = listOf(
        Thread(firstHalfWorker).apply { start() },
        Thread(secondHalfWorker).apply { start() }
    )

    try {
        threads.forEach { it.join() }
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    println("Sum = ${firstHalfWorker.sum + secondHalfWorker.sum}")
}

fun announceTask(nmb: Int) {
    println("=== Task $nmb ===")
}

class FirstTask(private val arr: IntArray) : Runnable {
    override fun run() {
        println("Sum = ${arr.sum()}")
    }
}

class SecondTask(val value: Double, val pow: Double) : Callable<Double> {
    override fun call(): Double {
        return value.pow(pow)
    }
}

class FifthTask(private val array: IntArray) : Runnable {
    var sum = 0
    override fun run() {
        for (value in array) {
            sum += value
        }
    }
}