package task_4_4

fun main() {
    val noSync = iNeedMoreCountersBiggerNumbersNoSync();
    println(noSync)
    val sync = iNeedMoreCountersBiggerNumbersSync();
    println(sync)
}

fun iNeedMoreCountersBiggerNumbersNoSync() : Int{
    var k = 0;
    val thread1 = Thread {
        for(i in 1..1000){
            k++;
        }
    }
    val thread2 = Thread {
        for(i in 1..1000){
            k++;
        }
    }
    val thread3 = Thread {
        for(i in 1..1000){
            k++;
        }
    }

    thread1.start();
    thread2.start();
    thread3.start();

    thread1.join();
    thread2.join();
    thread3.join();

    return k;
}

fun iNeedMoreCountersBiggerNumbersSync() : Int{
    var k = 0;
    val smth = arrayOf(1, 2);
    val thread1 = Thread {
        for(i in 1..1000){
            synchronized(smth) {
                k++;
            }
        }
    }
    val thread2 = Thread {
        for(i in 1..1000){
            synchronized(smth) {
                k++;
            }
        }
    }
    val thread3 = Thread {
        for(i in 1..1000){
            synchronized(smth) {
                k++;
            }
        }
    }

    thread1.start();
    thread2.start();
    thread3.start();

    thread1.join();
    thread2.join();
    thread3.join();

    return k;
}