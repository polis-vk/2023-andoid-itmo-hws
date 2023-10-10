import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future


fun main() {
    // Task 1
    val firstTask = Thread(RunCount(arrayOf(1, 5, 3, 8, -1, 4)))
    firstTask.start()

    // Task 2
    val executor = Executors.newSingleThreadExecutor()
    val a = (0..100).random()
    val b = (0..10).random()
    val callableTask = PowerCallable(a, b)
    val future: Future<Long> = executor.submit(callableTask)
    val result: Long = future.get()
    println("$a^$b = $result")
    executor.shutdown()

    // Task 3
    val resource1 = ResourceForThread()
    val resource2 = ResourceForThread()
    val thread1 = Thread {
        println("Ожидаем выполнения задачи 2 треда")
        while(!resource2.isComplete()) {
            resource2.complete()
        }
    }
    val thread2 = Thread {
        println("Ожидаем выполнения задачи 1 треда")
        while(!resource1.isComplete()) {
            resource2.isComplete()
        }
    }
    //Deadlock
//    thread1.start()
//    thread2.start()

    // Task 4
    val countWithSynchronized: Int = taskIncrementWithSynchronized()
    val countWithoutSynchronized: Int = taskIncrementWithoutSynchronized()
    println("Counter: $countWithoutSynchronized")
    println("Use synchronized: $countWithSynchronized")
}

// Task 1
class RunCount(private val arrayCount: Array<Int>) : Runnable {
    override fun run() {
        println(arrayCount.sum())
    }
}

// Task 2
class PowerCallable(val a: Int, val b: Int) : Callable<Long> {
    override fun call(): Long {
        var res: Long = 1
        for (i in 1..b) {
            Thread.sleep(1000)
            res *= a
        }
        return res
    }

}

// Task 3
class ResourceForThread {
    private var flag: Boolean = false

    fun isComplete() = flag

    fun complete() {
        flag = true
    }
}

// Task 4
fun taskIncrementWithSynchronized() : Int {
    var counter = 0
    val lockObject = Any()
    fun increment() {
        synchronized(lockObject) {
            counter++
        }
    }
    val thread1 = Thread {
        for (i in 0..999) {
            increment()
        }
    }
    val thread2 = Thread {
        for (i in 0..999) {
            increment()
        }
    }
    val thread3 = Thread {
        for (i in 0..999) {
            increment()
        }
    }
    thread1.start()
    thread2.start()
    thread3.start()
    try {
        thread1.join()
        thread2.join()
        thread3.join()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    return counter
}


fun taskIncrementWithoutSynchronized() : Int {
    var counter = 0
    val thread1 = Thread {
        for (i in 0..999) {
            counter++
        }
    }
    val thread2 = Thread {
        for (i in 0..999) {
            counter++
        }
    }
    val thread3 = Thread {
        for (i in 0..999) {
            counter++
        }
    }
    thread1.start()
    thread2.start()
    thread3.start()
    try {
        thread1.join()
        thread2.join()
        thread3.join()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    return counter
}
