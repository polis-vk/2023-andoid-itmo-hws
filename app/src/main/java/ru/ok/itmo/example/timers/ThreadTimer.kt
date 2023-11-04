package ru.ok.itmo.example.timers

class ThreadTimer(override var sleepTime: Long) : TimerProtocol {
    private var current = 0

    @Volatile
    private var isReset = true
        set(value) = synchronized(this) { field = value }

    override fun run(completion: (Int) -> Unit) {
        isReset = false
        val thread = Thread {
            while (!isReset && current < 100) {
                Thread.sleep(sleepTime)
                current++
                completion(current)
            }
        }
        thread.start()
    }

    override fun reset() {
        isReset = true
        current = 0
    }
}
