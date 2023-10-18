class A(val a: Int? = null) {
    @Synchronized
    fun printA() = print(a)
}

class B(val b: Int? = null) {
    @Synchronized
    fun printB() = print(b)
}

fun main() {
    val objA = A()
    val objB = B()

    val thread1 = Thread {
        synchronized(objB) {
            println("thread1 captured objB")
            Thread.sleep(500)
            if (objB.b == null) {
                println("thread1 is waiting objA")
                objA.printA()
            }
        }
    }

    val thread2 = Thread {
        synchronized(objA) {
            println("thread2 captured objA")
            Thread.sleep(500)
            if (objA.a == null) {
                println("thread2 is waiting objB")
                objB.printB()
            }
        }
    }

    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    println("All threads have completed execution")
}
