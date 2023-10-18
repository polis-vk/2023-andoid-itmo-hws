package task5

import java.util.*

class IterativeParallelism {

    fun sum(
        numberThreads: Int,
        values: List<Int>,
    ): Int {
        val splits = splitList(values, numberThreads)
        val results: MutableList<RunnableSum>

        val threadList: MutableList<Thread> = ArrayList()
        results = ArrayList(Collections.nCopies(splits.size, null))
        for (i in splits.indices) {
            val subList: List<Int> = splits[i]
            val rSum = RunnableSum(subList)
            results[i] = rSum
            val thread = Thread(rSum)
            threadList.add(thread)
            thread.start()
        }
        for (thread in threadList) {
            while (true) {
                try {
                    thread.join()
                    break
                } catch (ignored: InterruptedException) {
                }
            }
        }

        var totalSum = 0
        for (rSum in results) {
            totalSum += rSum.getResult()
        }

        return totalSum
    }

    companion object {
        @Throws(IllegalArgumentException::class)
        private fun <T> splitList(list: List<T>, realN: Int): List<List<T>> {
            var n = realN
            val result: MutableList<List<T>> = ArrayList()
            if (n > list.size) {
                n = list.size
            }
            if (list.isEmpty()) {
                throw IllegalArgumentException("List is empty")
            }
            val blockSize = list.size / n
            val remainder = list.size % n
            var i = 0
            var left = 0
            var currentChunkSize: Int
            while (i < n) {
                currentChunkSize = if (i < remainder) blockSize + 1 else blockSize
                result.add(list.subList(left, left + currentChunkSize))
                ++i
                left += currentChunkSize
            }
            return result
        }
    }
}