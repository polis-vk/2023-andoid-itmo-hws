package task5

fun main(args: Array<String>) {
    val arr = arrayOf(1, 2, 3, 4, 5, 6, 7)
    val first = ArraySum(arr.slice(0..<arr.size / 2).toTypedArray())
    val second = ArraySum(arr.slice(arr.size / 2..<arr.size).toTypedArray())
    val thread1 = Thread(first)
    val thread2 = Thread(second)
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    println("Sum of array ${arr.contentToString()} is ${first.getSum() + second.getSum()}")
}