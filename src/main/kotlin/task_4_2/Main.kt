package task_4_2

import java.util.concurrent.Executors

fun main() {
    val exeCUTE = Executors.newSingleThreadExecutor();
    val future = exeCUTE.submit(INeedMorePower(2.0,8));
    print(future.get());
    exeCUTE.shutdown();
}