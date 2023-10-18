package task5

fun main() {
    val worker = IterativeParallelism()

    val intList = listOf(1, 2, 3, 4, 5)

    println("The sum of the elements of the array: ${worker.sum(2, intList)}")
}