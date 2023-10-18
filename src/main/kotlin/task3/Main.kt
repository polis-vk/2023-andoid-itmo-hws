package task3

data class ComplicatedObject(private val value: Int)

fun main() {
    val obj1 = ComplicatedObject(1)
    val obj2 = ComplicatedObject(2)

    val thread1 = Thread {
        synchronized(obj1) {
            println("thread1 lock obj1")
            Thread.sleep(100)
            println("thread1 wait obj2")
            synchronized(obj2) {
                println("thread1 lock obj2")
            }
        }
    }

    val thread2 = Thread {
        synchronized(obj2) {
            println("thread2 lock obj2")
            Thread.sleep(100)
            println("thread2 wait obj1")
            synchronized(obj1) {
                println("thread2 lock obj1")
            }
        }
    }

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    println("Deadlock")
}