package ru.ok.itmo.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun run() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    for (i in 1..100) {
                        delay(100)
                        _counter.value = i
                    }
                }
            } catch (e : Throwable) {
                println(e.message)
            }
        }
    }
}