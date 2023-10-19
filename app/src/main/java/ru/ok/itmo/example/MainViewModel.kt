package ru.ok.itmo.example

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    val durationList = listOf(5, 10, 30, 60, 120).map { it * 1000 }

    var mode = Mode.THREAD

    @Volatile
    var selectedDuration = durationList.first()

    @Volatile
    private var current = 0
        set(value) = synchronized(this) { field = value }

    fun startOnThread(callback: (Int) -> Unit, onComplete: () -> Unit) {
        Thread {
            current = 0
            try {
                while (current < selectedDuration) {
                    Thread.sleep(100)
                    current += 100
                    callback(current)
                }
                onComplete()
            } catch (e: InterruptedException) {
                Log.d("THREAD", "Thread interrupted")
            }
        }.start()
    }

    fun reset() {
        current = 0
    }
    fun startFlow() = flow {
        current = 0
        emit(current)
        while (current < selectedDuration) {
            delay(100)
            current += 100
            emit(current)
        }
    }

    enum class Mode {
        FLOW, THREAD
    }
}