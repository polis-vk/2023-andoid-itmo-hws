import java.util.ArrayList

private var counter = 0
private val lockObject = Any()

private fun increment() {
    synchronized(lockObject) {
        counter++
    }
}

fun main(args: Array<String>) {
    println("task 4")
    val threadList = ArrayList<Thread>()

    for (i in 0 until 3) {
        val thread = Thread {
            for (j in 0 until 1000) {
                increment()
            }
        }
        threadList.add(thread)
        thread.start()
    }

    for (thread in threadList) {
        try {
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    println("Значение counter: $counter")
}
