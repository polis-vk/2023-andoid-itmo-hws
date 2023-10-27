package ru.ok.itmo.example.counters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelWorkerFactory(
    private val counter: Counter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoroutinesViewModelWorker::class.java)) {
            return CoroutinesViewModelWorker(counter) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}