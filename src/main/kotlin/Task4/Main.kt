package Task4

//Synchronized

fun main(args: Array<String>){
    val counter = Counter()
    synchronization(counter)
    asynchronization(counter)
}

fun synchronization(counter: Counter){
    val synchronization = Runnable {
        synchronized(counter){
            for (i in (1..1000) ){
                counter.plus()
            }
        }
    }
    val array = listOf( Thread(synchronization), Thread(synchronization), Thread(synchronization) )
    array.forEach{
        it.start()
    }
    array.forEach{
        it.join()
    }
    val result = counter.get()
    println("With synchronization counter = $result")
    counter.zero()
}
fun asynchronization(counter: Counter){
    val asynchronization = Runnable {
            for (i in (1..1000) ){
                counter.plus()
        }
    }
    val array = listOf( Thread(asynchronization), Thread(asynchronization), Thread(asynchronization) )
    array.forEach{
        it.start()
    }
    array.forEach{
        it.join()
    }
    val result = counter.get()
    println("Without synchronization counter = $result")
    counter.zero()
}
class Counter{
    private var count = 0
    fun plus(){
        count++
    }
    fun get(): Int {
        return count
    }
    fun zero() {
        count = 0
    }
}