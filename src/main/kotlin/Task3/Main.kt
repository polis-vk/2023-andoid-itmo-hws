package Task3

//Deadlock

fun main(args: Array<String>) {
    val size = 5
    val sleepTime: Long = 100
    val array1 = Array<Int>(size) {item -> (0..50).random()}
    val array2 = Array<Int>(size) {item -> (50..100).random()}

    val thread1 = Thread() {
        synchronized(array1) {
            val sum = array1.sum()
            println(Thread.currentThread())
            Thread.sleep(sleepTime)
            println("We are in deadlock of thread1")
            synchronized(array2) {
                array2.map { item -> item + sum }
            }
        }
    }

    val thread2 = Thread() {
        synchronized(array2) {
            val sum = array2.sum()
            println(Thread.currentThread())
            Thread.sleep(sleepTime)
            println("We are in deadlock of thread2")
            synchronized(array1) {
                array1.map { item -> item + sum }
            }
        }
    }

    thread1.start()
    thread2.start()
}