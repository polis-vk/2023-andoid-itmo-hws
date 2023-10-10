package task2

import java.util.concurrent.Callable

class Pow(private val number: Int, private val power: Int) : Callable<Long> {
    override fun call(): Long {
        return binPow(number, power)
    }

    private fun binPow(a: Int, n: Int): Long {
        if (n == 0) return 1
        if (n == 1) return a.toLong()
        if (n % 2 == 1) return a.toLong() * binPow(a, n - 1)
        val res = binPow(a, n / 2)
        return res * res
    }

}