package task1

fun main(args: Array<String>) {
    val task1Thread = Thread(SumClass(arrayOf(1, 2, 3)))
    task1Thread.start()
}
