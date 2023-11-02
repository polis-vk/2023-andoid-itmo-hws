package ru.ok.itmo.example.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StateFlowViewModel : ViewModel() {
    private var timerJob: Job? = null

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    fun start() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            var current = 0
            _stateFlow.emit(current)
            while (current < TARGET_DURATION) {
                delay(100)
                current += 1
                _stateFlow.emit(current)
            }
        }
    }

    companion object {
        private const val TARGET_DURATION = 100
    }
}