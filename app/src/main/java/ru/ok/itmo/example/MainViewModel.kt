package ru.ok.itmo.example

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun run() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repeat(101) {
                        delay(100)
                        _count.value = it
                    }
                }
            } catch (error: Throwable) {
                println("Error $error")
            }
        }
    }
}