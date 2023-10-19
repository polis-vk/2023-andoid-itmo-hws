object Resources {
    var resource1 = 1
    var resource2 = 2
}

class Task3A(): Runnable {
    override fun run() {
        synchronized(Resources.resource1) {
            println("Process A is using resource 1")
            Thread.sleep(300)
            synchronized(Resources.resource2) {
                println("Process A is using resource 2")
            }
        }
    }
}

class Task3B(): Runnable {
    override fun run() {
        synchronized(Resources.resource2) {
            println("Process B is using resource 2")
            Thread.sleep(300)
            synchronized(Resources.resource1) {
                println("Process B is using resource 1")
            }
        }
    }
}

fun main() {
    val run1 = Task3A()
    val run2 = Task3B()
    val thread1 = Thread(run1)
    val thread2 = Thread(run2)
    thread1.start()
    thread2.start()
}