package task_4_2

import java.util.concurrent.Executors

fun main() {
    val arrDown = intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    val arrUp = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    val thread1 = Thread{
        println("thread 1 begin (Never gonna give you up)")
        synchronized(arrDown){
            Thread.sleep(100);
            synchronized(arrUp){
            }
        }
        println("thread 1 end")
    }

    val thread2 = Thread{
        println("thread 2 begin (Never gonna let you down)")
        synchronized(arrUp){
            synchronized(arrDown){
            }
        }
        println("thread 2 end")
    }

    thread1.start();
    thread2.start();
}