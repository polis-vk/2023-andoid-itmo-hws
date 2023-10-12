package task4

import kotlin.concurrent.thread

class SyncCounter {
    var cnt: Int = 0
    fun inc() {
        synchronized(this) {
            cnt++
        }
    }
}
class NotSyncCounter {
    var cnt: Int = 0
    fun inc() {
        cnt++
    }
}

fun main() {
    val syncCounter = SyncCounter()
    val notSyncCounter = NotSyncCounter()

    List(3) {
        thread {
            repeat(1000) {
                syncCounter.inc()
                notSyncCounter.inc()
            }
        }
    }.forEach { it.join() }
    println("Synchronized counter: ${syncCounter.cnt}")
    println("Synchronized counter: ${notSyncCounter.cnt}")
}