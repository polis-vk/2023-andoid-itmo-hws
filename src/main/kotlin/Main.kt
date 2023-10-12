package main.kotlin

import java.util.concurrent.*
import kotlin.math.pow

fun main() {
    testPart5()
}

// PART 1
class IntSumArray(private val arr: IntArray) : Runnable {
    override fun run() {
        println(arr.sum())
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
        return "SynchronizedTotalizator:$cnt"
    }
}


class UnSynchronizedTotalizator {
    private var cnt: Int = 0
    fun inc(): Int {
        return ++cnt
    }

    override fun toString(): String {
        return "UnSynchronizedTotalizator:$cnt"
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

// PART 5

class ConcurrentIntSumArray(private val arr: IntArray, private val iterateType: IterateType) : Runnable {
    enum class IterateType(val startPosition: Int) {
        EVEN(0), UNEVEN(1)
    }
    var sum = 0
        private set
    override fun run() {
        for(i in iterateType.startPosition..arr.size step 2) {
            sum += i
        }
    }
}

fun testPart5() {
    val array =  IntArray(10000000){ (0..1000).random() }
    val evenIntSumArray= ConcurrentIntSumArray(array, ConcurrentIntSumArray.IterateType.EVEN)
    val unevenIntSumArray= ConcurrentIntSumArray(array, ConcurrentIntSumArray.IterateType.UNEVEN)
    val thread1 = Thread(evenIntSumArray)
    val thread2 = Thread(unevenIntSumArray)
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    val result = evenIntSumArray.sum + unevenIntSumArray.sum
    println(result)
}
