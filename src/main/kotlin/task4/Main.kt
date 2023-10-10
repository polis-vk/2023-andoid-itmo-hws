package task4

fun main(args: Array<String>) {
    val increments = 10000
    println("With synchronized: ${withSynchronized(increments)}")
    println("Without synchronized: ${withoutSynchronized(increments)}")

}
fun withSynchronized(increments: Int): Int {
    val counter = Counter()
    val runnable = Runnable {
        for (i in 1..increments) {
            synchronized(counter) {
                counter.inc()
            }
        }
    }
    val threads = createThreads(3, runnable)
    startAll(*threads)
    joinAll(*threads)
    return counter.get()
}

fun withoutSynchronized(increments: Int): Int {
    val counter = Counter()
    val runnable = Runnable {
        for (i in 1..increments) {
            counter.inc()
        }
    }
    val threads = createThreads(3, runnable)
    startAll(*threads)
    joinAll(*threads)
    return counter.get()
}

fun joinAll(vararg threads: Thread) {
    for (thread in threads) {
        thread.join()
    }
}

fun startAll(vararg threads: Thread) {
    for (thread in threads) {
        thread.start()
    }
}

fun createThreads(n: Int, runnable: Runnable): Array<Thread> {
    val arr = ArrayList<Thread>()
    for (i in 0..<n) {
        arr.add(Thread(runnable))
    }
    return arr.toTypedArray()
}