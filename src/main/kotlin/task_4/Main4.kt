package task_4



private var usualCounter = 0
private var syncCounter = 0
private val resourceToBlock = Any()


fun main() {

    usualCount()
    syncCount()

}


fun usualInc() {
    for (i in 1..1000) {
        usualCounter++
    }
}

fun usualCount () {


    val thread1 = Thread {
        usualInc()
    }

    val thread2 = Thread {
        usualInc()
    }

    val thread3 = Thread {
        usualInc()
    }

    thread1.start()
    thread2.start()
    thread3.start()


    try {
        thread1.join()
        thread2.join()
        thread3.join()
    } catch (e : InterruptedException) {
        // nothing
    }

    println("Usual counting result is $usualCounter")
}


fun syncInc() {
    synchronized(resourceToBlock) {
        for (i in 1..1000) {
            syncCounter++
        }
    }
}

fun syncCount () {
    val thread1 = Thread {
        syncInc()

    }

    val thread2 = Thread {
        syncInc()
    }

    val thread3 = Thread {
        syncInc()
    }

    thread1.start()
    thread2.start()
    thread3.start()

    try {
        thread1.join()
        thread2.join()
        thread3.join()
    } catch (e : InterruptedException) {
        // nothing
    }

    println("Sync counting result is $syncCounter")
}