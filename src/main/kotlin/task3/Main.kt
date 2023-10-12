package task3

fun main() {
    val resource1 = Any()
    val resource2 = Any()
    val thread1 = Thread {
        synchronized(resource1) {
            println("Поток 1: Захватил ресурс 1")
            Thread.sleep(100)
            println("Поток 1: Ожидает ресурс 2")
            synchronized(resource2) {
                println("Поток 1: Захватил ресурс 2")
            }
        }
    }
    val thread2 = Thread {
        synchronized(resource2) {
            println("Поток 2: Захватил ресурс 2")
            Thread.sleep(100)
            println("Поток 2: Ожидает ресурс 1")
            synchronized(resource1) {
                println("Поток 2: Захватил ресурс 1")
            }
        }
    }
    thread1.start()
    thread2.start()
}