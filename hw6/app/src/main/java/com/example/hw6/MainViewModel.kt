package com.example.hw6

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {


    private val _uiStateLiveData = MutableLiveData<TimerUiState>()
    val uiStateLiveData: LiveData<TimerUiState>
        get() = _uiStateLiveData

    var time : Int = 0
    class Factory internal constructor() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }

    fun countdown(mode : Int) {
        if (mode == 0) {
            ruwCoroutine()
        } else {
            runFlow()
        }
    }

    fun makeAlarm(context : Context, timeInMillis : Long) {
        viewModelScope.launch {
            AlarmFlow.scheduleAlarmWork(context, timeInMillis)
        }
    }
    private fun timerFlow() : Flow<Int> {

        val fl: Flow<Int> = flow {
            var time: Int = 0
            while (time <= 100) {
                delay(100)
                emit(time)
                time += 10
            }
        }
        return fl
    }
    private fun runFlow() {
        viewModelScope.launch {
            timerFlow()
                .flowOn(Dispatchers.IO)
                .collect { handleResult(it) }

        }
    }

    private fun ruwCoroutine() {
        viewModelScope
            .launch {
                var time: Int = 0
                while (time <= 100) {
                    withContext(Dispatchers.IO) {
                        delay(100)
                    }
                    handleResult(time)
                    time += 10
                }
            }
    }

    private fun handleResult(state : Int) {
        _uiStateLiveData.value = TimerUiState.Data(state)
    }

}