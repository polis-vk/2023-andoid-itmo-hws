package `4task`

import java.util.concurrent.Executors
import kotlin.math.pow
import kotlin.random.Random
import java.util.concurrent.Callable

fun main(args: Array<String>) {
    val container = ValContainer()
    val sycRunnable = Runnable {
        synchronized(container) {
            // 1000 слишком мало, наверное, успевают обработаться за такт и разницы нет,
            // а так разница ощутима
            for (i in (1..2000)) {
                container.inc()
            }
        }
    }
    var arr = listOf(Thread(sycRunnable), Thread(sycRunnable), Thread(sycRunnable))
    for (thread in arr) {
        thread.start()
    }
    for (thread in arr) {
        thread.join()
    }
    var res = container.get()
    println("With synchronized: $res")
    container.boot()
    val notSycRunnable = Runnable {
        for (i in (1..2000)) {
            container.inc()
        }
    }
    arr = listOf(Thread(notSycRunnable), Thread(notSycRunnable), Thread(notSycRunnable))
    for (thread in arr) {
        thread.start()
    }
    for (thread in arr) {
        thread.join()
    }
    res = container.get()
    println("Without synchronized: $res")
}

class ValContainer{
    private var value = 0

    fun inc() {
        value++
    }

    fun get() : Int {
        return value
    }

    fun boot() {
        value = 0
    }
}