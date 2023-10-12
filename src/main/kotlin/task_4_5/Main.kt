package task_4_5


fun main() {
    val arr1 = intArrayOf(1, 2, 3, 4, 5, 6);
    val arrFirstHalf = arr1.slice(0..arr1.size/2);
    val arrSecondHalf = arr1.slice(arr1.size/2+1..arr1.size - 1);
    val run1 = RunnableDanteRunnable(arrFirstHalf);
    val run2 = RunnableDanteRunnable(arrSecondHalf);
    val thread1 = Thread(run1);
    val thread2 = Thread(run2);

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    println(run1.res() + run2.res());

}

class RunnableDanteRunnable(val array: List<Int>) : Runnable {
    var sum = 0;

    override fun run() {
        for(element in array){
            sum += element;
        }
    }

    fun res(): Int{
        return sum;
    }

}