package task3

object A {
    @Synchronized
    fun printMsg() {
        println("access class A")
        B.printMsg()
    }
}

object B {
    @Synchronized
    fun printMsg() {
        println("access class B")
        A.printMsg()
    }
}

fun main() {
    val thread1 = Thread {
        A.printMsg()
    }
    val thread2 = Thread {
        B.printMsg()
    }
    thread1.start()
    thread2.start()
}
