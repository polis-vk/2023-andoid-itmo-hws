package ru.ok.itmo.example.timers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FlowTimer(override var sleepTime: Long) : TimerProtocol {
    private var current = 0
    private var job: Job? = null

    override fun run(completion: (Int) -> Unit) {
        job = MainScope().launch(Dispatchers.Main) {
            flow {
                while (current < 100) {
                    delay(sleepTime)
                    current++
                    emit(current)
                }
            }.flowOn(Dispatchers.IO).collect { completion(it) }
        }
    }

    override fun reset() {
        job?.cancel()
        current = 0
    }
}
