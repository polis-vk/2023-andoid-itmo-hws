package main.kotlin

import java.util.concurrent.*
import kotlin.math.pow

fun main() {
    testPart4()
}

// PART 1
class IntSumArray(private val args: IntArray) : Runnable {
    override fun run() {
        println(args.sum())
    }

}

fun testPart1() {
    val array = intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val intArraySum = IntSumArray(array)
    val task = Thread(intArraySum)
    task.start()
    task.join()
}

// PART 2

class PowWorker(private val n: Double, private val m: Double) : Callable<Double> {
    override fun call(): Double {
        return n.pow(m)
    }

}

fun testPart2() {
    val powWorker = PowWorker(2.0, 10.0)
    val service = Executors.newSingleThreadExecutor()
    val future = service.submit(powWorker)
    println(future.get())
}

// PART 3

class DeadlockTasks {
    companion object {
        const val SLEEP_CONSTANT = 100L
    }

    object Task1 {
        @Synchronized
        fun run() {
            Thread.sleep(SLEEP_CONSTANT)
            Task2.run()
        }
    }

    object Task2 {
        @Synchronized
        fun run() {
            Thread.sleep(SLEEP_CONSTANT)
            Task1.run()
        }
    }
}

fun testPart3() {
    val thread1 = Thread {
        DeadlockTasks.Task1.run()
    }
    val thread2 = Thread {
        DeadlockTasks.Task2.run()
    }

    thread1.start()
    thread2.start()
}

// PART 4

class SynchronizedTotalizator {
    @Volatile
    private var cnt: Int = 0

    @Synchronized
    fun inc(): Int {
        synchronized(this) {
            return ++cnt
        }
    }

    override fun toString(): String {
        return "main.kotlin.SynchronizedTotalizator:$cnt"
    }
}


class UnSynchronizedTotalizator {
    private var cnt: Int = 0
    fun inc(): Int {
        return ++cnt
    }

    override fun toString(): String {
        return "main.kotlin.UnSynchronizedTotalizator:$cnt"
    }
}

fun testPart4() {
    val synchronizedTotalizator = SynchronizedTotalizator()

    val threadsForSync = (0..2).map {
        Thread {
            repeat(1000) {
                synchronizedTotalizator.inc()
            }
        }
    }.onEach(Thread::start)

    threadsForSync.forEach(Thread::join)
    println(synchronizedTotalizator)

    val unSynchronizedTotalizator = UnSynchronizedTotalizator()

    val threadsForUnSync = (0..2).map {
        Thread {
            repeat(1000) {
                unSynchronizedTotalizator.inc()
            }
        }
    }.onEach(Thread::start)

    threadsForUnSync.forEach(Thread::join)
    println(unSynchronizedTotalizator)
}
