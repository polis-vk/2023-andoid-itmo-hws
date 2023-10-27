package ru.ok.itmo.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProgressViewModel : ViewModel() {
    private val _progress = MutableStateFlow(0)
    val progress = _progress.asStateFlow()

    fun run(max: Int) = CoroutineScope(Dispatchers.IO).launch {
        repeat(max + 1) {
            delay(100)
            _progress.value = it
        }
    }
}
