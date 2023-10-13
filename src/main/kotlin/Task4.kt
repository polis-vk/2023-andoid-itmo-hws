


import kotlin.concurrent.thread

fun main() {
    // Unsafe
    val counter1 = Counter()

    val thread1 = thread { unsafeIncrementCounter(counter1) }
    val thread2 = thread { unsafeIncrementCounter(counter1) }
    val thread3 = thread { unsafeIncrementCounter(counter1) }

    thread1.join()
    thread2.join()
    thread3.join()

    println("Счетчик без синхронизации: ${counter1.count}")


    // Safe
    val counter2 = Counter()

    // потоки
    val thread4 = thread { safeIncrementCounter(counter2) }
    val thread5 = thread { safeIncrementCounter(counter2) }
    val thread6 = thread { safeIncrementCounter(counter2) }

    thread4.join()
    thread5.join()
    thread6.join()
    println("Счетчик с синхронизацией: ${counter2.count}")
}

class Counter {
    var count = 0

    fun atomicIncrement() {
        synchronized(this) {
            count++
        }
    }
}

fun safeIncrementCounter(counter: Counter) {
    for (i in 0 until 10000) {
        counter.atomicIncrement()
    }
}

fun unsafeIncrementCounter(counter: Counter) {
    for (i in 0 until 10000) {
        counter.count++
    }
}
// Счетчик без синхронизации: 24055
// Счетчик с синхронизацией: 30000