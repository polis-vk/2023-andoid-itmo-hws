package third

fun main() {
    val arr1 = arrayOf(1, 2, 3, 4, 5)
    val arr2 = arrayOf(6, 7, 8, 9)

    val thread1 = Thread() {
        synchronized(arr1) {
            val sum = arr1.sum()
            Thread.sleep(100)
            println("deadlock 1")
            synchronized(arr2) {
                arr2.map { el -> el + sum }
            }
        }
    }

    val thread2 = Thread() {
        synchronized(arr2) {
            val sum = arr2.sum()
            Thread.sleep(100)
            println("deadlock 2")
            synchronized(arr1) {
                arr1.map { el -> el + sum }
            }
        }
    }

    thread1.start()
    thread2.start()
}