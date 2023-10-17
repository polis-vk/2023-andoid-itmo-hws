package task4

private var counter = 0
private val lock = Any()

fun inc() {
    synchronized(lock) {
        for (i in 1..1000) {
            counter++
        }
    }
}

fun main() {
    val thread1 = Thread {
        inc()
    }
    val thread2 = Thread {
        inc()
    }
    val thread3 = Thread {
        inc()
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

    println(counter)
    //Без синхронизации мы получаем undefined behaviour. Т.е. не всегда получим 3000
}
