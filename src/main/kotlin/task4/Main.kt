package task4

fun main() {
    println(
        "Synchronized: ${
            work {
                synchronized(it)
                {
                    it.count++
                }
            }
        }"
    )
    println(
        "Non Synchronized: ${
            work {
                it.count++
            }
        }"
    )
}