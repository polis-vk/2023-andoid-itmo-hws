package ru.ok.itmo.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Task4ViewModel : ViewModel() {
    private val _progressFlow = MutableStateFlow(PROGRESS_MIN_VALUE)
    val progressFlow: StateFlow<Int> get() = _progressFlow
    private var progressJob: Job? = null

    fun startProgress() {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            var progress = PROGRESS_MIN_VALUE
            while (true) {
                delay(100)
                progress++
                _progressFlow.emit(progress)
                if (progress >= PROGRESS_MAX_VALUE) break
            }
        }
    }

    companion object {
        private const val PROGRESS_MIN_VALUE = 0
        private const val PROGRESS_MAX_VALUE = 100
    }
}