package task3

fun main(args: Array<String>) {
    val obj1 = Any()
    val obj2 = Any()
    val thread1 = Thread {
        synchronized(obj1) {
            println("Thread 1 Захватил obj1")
            Thread.sleep(100)
            synchronized(obj2) {
                println("Thread 1 Захватил obj2")
            }
        }
    }

    val thread2 = Thread {
        synchronized(obj2) {
            println("Thread 2 Захватил obj2")
            Thread.sleep(100)
            synchronized(obj1) {
                println("Thread 2 Захватил obj1")
            }
        }
    }
    thread1.start()
    thread2.start()
}