package task4

import java.util.ArrayList

val work = Work()

class Work : ((Counter) -> Unit) -> Counter {
    override fun invoke(action: (Counter) -> Unit): Counter {
        val counter = Counter()
        val threadList: MutableList<Thread> = ArrayList()
        for (i in 0 until Counter.NUMBER_THREADS) {
            val thread = Thread {
                for (j in 0 until Counter.DESIRED_NUMBER) {
                    action(counter)
                }
            }
            threadList.add(thread)
            thread.start()
        }
        Helper.waitThreads(threadList)
        return counter
    }
}