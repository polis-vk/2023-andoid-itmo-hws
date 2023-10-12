import java.math.BigInteger
import java.util.concurrent.Callable
import java.util.concurrent.Executors


fun main() {
    task5()
}

fun task1() {
    class Sum(val iterable: Iterable<Int>) : Runnable {
        override fun run() = println(iterable.sumOf { it })
    }
    Thread(Sum(listOf(1, 2, 3, 4, 1, 4, 2))).start()
}


fun task2() {
    class Pow(val a: Int, val k: Int) : Callable<BigInteger> {
        private fun binpow(n: Int): BigInteger =
            if (n == 0) 1.toBigInteger()
            else {
                if (n % 2 == 1) {
                    binpow(n - 1) * a.toBigInteger()
                } else {
                    binpow(n / 2).let { it * it }
                }
            }

        override fun call(): BigInteger = binpow(k)
    }

    val tasks = List(2000) { Pow((1..200).random(), (1..200).random()) }
    Executors.newWorkStealingPool().use { executor ->
        tasks.map {
            it to executor.submit(it)
        }.forEachIndexed { i, (x, r) ->
            println("$i: ${x.a}^${x.k} = ${r.get()}")
        }
    }
}

fun task3() {
    val a = "amogus"
    val b = "sus"

    fun simple(order: Boolean): () -> Unit = {
        val first = if (order) a else b
        val second = if (order) b else a
        synchronized(first) {
            println("first lock")
            println(first)
            Thread.sleep(100)
            synchronized(second) {
                println("second lock")
                println(second)
            }
        }
    }

    Thread(simple(true)).start()
    Thread(simple(false)).start()
}

fun task4() {
    class Counter {
        var n: Int = 0
            private set

        fun inc() = ++n
    }

    class ConcurrentCounter {
        var n: Int = 0
            private set

        fun inc() = synchronized(this) { ++n }
    }

    val cnt1 = ConcurrentCounter()
    val cnt2 = Counter()
    List(3) {
        Thread {
            repeat(1000) {
                cnt1.inc()
                cnt2.inc()
            }
        }.also { it.start() }
    }.forEach { it.join() }
    println("Concurrent: " + cnt1.n)
    println("Simple Counter: " + cnt2.n)
}

fun task5() {
    class Sum(val a: List<Int>) : Callable<Int> {
        override fun call() = a.sum()
    }

    val threadsCnt = 20
    val executor = Executors.newFixedThreadPool(threadsCnt)
    val values = List(1000500) { (1..50).random() }
    val k: Int = values.size / threadsCnt
    val mod: Int = values.size % threadsCnt
    List(threadsCnt) { i: Int ->
        executor.submit(
            Sum(
                values.subList(
                    i * k + minOf(i, mod),
                    minOf(
                        (i + 1) * k + minOf(i + 1, mod),
                        values.size
                    )
                )
            )
        )
    }.map {
        it.get()
    }.let {
        println(it)
        require(it.sum() == values.sum())
        println(it.sum())
    }
}