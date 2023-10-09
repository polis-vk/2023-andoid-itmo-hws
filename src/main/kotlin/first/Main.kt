package first

fun main() {
    val array = arrayOf(1, 2, 3, 4, 6)
    val t = Thread(SumRun(array))

    t.start()
}