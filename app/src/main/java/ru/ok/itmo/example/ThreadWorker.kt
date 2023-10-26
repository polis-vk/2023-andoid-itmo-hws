package ru.ok.itmo.example

import android.app.Activity

class ThreadWorker(
    private val counter: Counter,
    private val updateUi: (value: Int) -> Unit,
    private val endUpdateUi: () -> Unit,
    private val activity: Activity
) : Worker {
    private lateinit var thread: Thread
    private lateinit var runnableWorker: RunnableWorker
    override fun run(time: Long) {
        runnableWorker = RunnableWorker(time, counter, updateUi, endUpdateUi, activity)
        thread = Thread(runnableWorker)
        thread.start()
    }

    override fun stop() {
        if (::runnableWorker.isInitialized)
            runnableWorker.stop()
    }
}