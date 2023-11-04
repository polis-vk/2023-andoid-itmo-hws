package ru.ok.itmo.example.Timers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope

class StateFlowTimer(override var sleepTime: Long) : TimerProtocol {
    class FlowViewModel(private val sleepTime: Long) : ViewModel() {
        private val _stateFlow = MutableStateFlow(0)
        val stateFlow = _stateFlow.asStateFlow()

        private var current = 0

        fun start() = viewModelScope.launch(Dispatchers.IO) {
            while (current < 100) {
                delay(sleepTime)
                current++
                _stateFlow.value = current
            }
        }

        fun end() {
            current = 0
        }
    }

    private val viewModel = FlowViewModel(sleepTime)
    private var job: Job? = null

    override fun run(completion: (Int) -> Unit) {
        job = viewModel.start().also {
            MainScope().launch(Dispatchers.Main) {
                viewModel.stateFlow.collect {
                    completion(it)
                }
            }
        }
    }

    override fun reset() {
        job?.cancel()
        viewModel.end()
    }
}
