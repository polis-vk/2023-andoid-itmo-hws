package task_4_1

class RunnableDanteRunnable(val array: IntArray) : Runnable {
    override fun run() {
        var sum = 0;
        for(element in array){
            sum += element;
        }
        println(sum);
    }
}