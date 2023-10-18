package task4

class Helper {
    companion object {
        fun waitThreads(threadList: MutableList<Thread>) {
            for (thread in threadList) {
                while (true) {
                    try {
                        thread.join()
                        break
                    } catch (ignored: InterruptedException) {
                    }
                }
            }
        }
    }
}