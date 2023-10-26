package ru.ok.itmo.example

import android.app.Activity

class RunnableWorker(
    private val time: Long,
    private val counter: Counter,
    private val updateUi: (value: Int) -> Unit,
    private val endUpdateUi: () -> Unit,
    private val activity: Activity
) : Runnable {
    private var isRunning = true

    override fun run() {
        counter.initCounter()

        do {
            activity.runOnUiThread {
                updateUi(counter.value)
            }
            Thread.sleep(time)
        } while (counter.updateCounter() && isRunning)

        activity.runOnUiThread {
            endUpdateUi()
        }
    }

    fun stop() {
        isRunning = false
    }
}