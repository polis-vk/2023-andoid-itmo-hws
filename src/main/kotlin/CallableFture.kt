import java.util.concurrent.Callable
import java.util.concurrent.Executors

fun main(){
    val executor = Executors.newSingleThreadExecutor()
    val callable = Pow(3, 2)
    val future = executor.submit(callable)
    println(future.get())
    executor.shutdown()
}

class Pow(private val a: Int, private val n: Int) : Callable<Int> {
    private fun pow(a:Int, n:Int) : Int =
        if (n == 0) 1
        else {
            val b = pow(a, n / 2)
            if (n % 2 == 1) b * b * a
            else b * b
        }
    override fun call(): Int = pow(a, n)
}