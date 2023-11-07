package task_4_3


fun main() {
    val arrDown = intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    val arrUp = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    val thread1 = Thread{
        println("thread 1 begin (Never gonna let you down)")
        synchronized(arrDown){
            synchronized(arrUp){
            }
        }
        println("thread 1 end")
    }

    val thread2 = Thread{
        println("thread 2 begin (Never gonna give you up)")
        synchronized(arrDown){
            synchronized(arrUp) {
                thread1.start();
                thread1.join();
            }
        }
        println("thread 2 end")
    }

    thread2.start();
}