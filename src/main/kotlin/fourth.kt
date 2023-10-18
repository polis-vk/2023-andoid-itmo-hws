class Counter {
    var count = 0

    fun increment() {
        count++
    }

    @Synchronized
    fun incrementWithSync() {
        count++
    }
}

fun main() {
    val counter = Counter()

    val thread1 = Thread {
        repeat(1000) {
            counter.incrementWithSync()
        }
    }

    val thread2 = Thread {
        repeat(1000) {
            counter.incrementWithSync()
        }
    }

    val thread3 = Thread {
        repeat(1000) {
            counter.incrementWithSync()
        }
    }

    thread1.start()
    thread2.start()
    thread3.start()

    thread1.join()
    thread2.join()
    thread3.join()

    println("Result with sync: ${counter.count}")

    // Without sync
    val counter2 = Counter()
    val thread4 = Thread {
        repeat(1000) {
            counter2.increment()
        }
    }

    val thread5 = Thread {
        repeat(1000) {
            counter2.increment()
        }
    }

    val thread6 = Thread {
        repeat(1000) {
            counter2.increment()
        }
    }

    thread4.start()
    thread5.start()
    thread6.start()

    thread4.join()
    thread5.join()
    thread6.join()

    println("Result without sync: ${counter2.count}")
}
