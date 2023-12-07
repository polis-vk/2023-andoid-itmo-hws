package ru.ok.itmo.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class Task4ViewModel : ViewModel() {

    private val _progress = MutableLiveData<Int>()
    val progress: MutableLiveData<Int>
        get() = _progress

    init {
        _progress.value = 0
    }

    fun startWork() {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in 0..100) {
                withContext(Dispatchers.Main) {
                    _progress.value = i
                }
                delay(100)
            }
        }
    }
}
