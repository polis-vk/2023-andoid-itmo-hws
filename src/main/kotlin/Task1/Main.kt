package Task1

//Runnable

fun main(args: Array<String>){
    val size = 10
    val array = Array<Int>(size) { item -> (0..100).random()}
    array.forEach{
        print(it)
        print(" ")
    }
    val thread = Thread( SumRun(array) )
    thread.start()
}

class SumRun(val array: Array<Int>) : Runnable {
    override fun run(){
        println() //This is for new line for sum, cause first println eats last print from forEach and i don't remember how to fix it
        println( array.sum() )
    }
}