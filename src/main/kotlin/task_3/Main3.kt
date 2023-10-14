package task_3


fun main() {

    val resource = Any()

    val thread1 = Thread {
        println("Thread 1 started")
        synchronized(resource) {
        }
        println("Thread 1 done")

    }

    val thread2 = Thread {
        println("Thread 2 started")
        synchronized(resource) {
            thread1.start()
            thread1.join()
        }
        println("Thread 2 done")
    }

    thread2.start()
}