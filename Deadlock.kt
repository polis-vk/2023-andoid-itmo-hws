fun main() {
    var t1: Thread? = null
    var t2: Thread? = null
    t1 = Thread{
        t2?.start()
        t2?.join()
    }
    t2 = Thread{
        t1?.join()
    }
    t1?.start()
    t1.join()
}