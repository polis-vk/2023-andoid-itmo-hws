var counter: Int = 0

fun runThreads(f: () -> Unit){
    counter = 0
    val t1 = Thread{
        f()
    }
    val t2 = Thread{
        f()
    }
    val t3 = Thread{
        f()
    }
    t1.start()
    t2.start()
    t3.start()
    t1.join()
    t2.join()
    t3.join()
    println(counter)
}

fun main() {
    val lock = Any()
    runThreads {
        for (i in 0..999) {
            synchronized(lock) {
                counter += 1
            }
        }
    }
    runThreads {
        for (i in 0..999) {
            counter += 1
        }
    }
}