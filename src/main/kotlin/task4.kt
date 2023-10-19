object Data {
    var counter: Int = 0
    var isSynchronised = false
    var resource: Any = 0
}

class Task4(): Runnable {
    override fun run() {
        for (i in 0..999) {
            if (Data.isSynchronised) {
                synchronized(Data.resource) {
                    Data.counter++
                }
            } else {
                Data.counter++
            }
        }
    }
}

fun main() {
    val run = Task4()

    Data.counter = 0
    Data.isSynchronised = false

    val th1 = Thread(run)
    val th2 = Thread(run)
    val th3 = Thread(run)

    th1.start()
    th2.start()
    th3.start()

    try {
        th1.join()
        th2.join()
        th3.join()
    } catch (e : InterruptedException) {
        e.printStackTrace()
    }

    println(Data.counter)

    Data.counter = 0
    Data.isSynchronised = true

    val th4 = Thread(run)
    val th5 = Thread(run)
    val th6 = Thread(run)

    th4.start()
    th5.start()
    th6.start()

    try {
        th4.join()
        th5.join()
        th6.join()
    } catch (e : InterruptedException) {
        e.printStackTrace()
    }

    println(Data.counter)
}