package ru.ok.itmo.example.Timers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutinesTimer(override var sleepTime: Long) : TimerProtocol {
    private var current = 0
    private var job: Job? = null

    override fun run(completion: (Int) -> Unit) {
        job = MainScope().launch(Dispatchers.IO) {
            while (current < 100) {
                delay(sleepTime)
                current++
                withContext(Dispatchers.Main) {
                    completion(current)
                }
            }
        }
    }

    override fun reset() {
        job?.cancel()
        current = 0
    }
}
