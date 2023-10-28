package ru.ok.itmo.example

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    private var started = false
    val progress: MutableLiveData<Int> = MutableLiveData(0)
    fun startProgressBarCoroutines() {
        if (started) return
        started = true
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(MainActivity.TAG, "run...")
            for (i in 0..100) {
                delay(100)
                withContext(Dispatchers.Main) {
                    progress.value = i
                }
            }
            started = false
        }.start()
    }

    fun startProgressBarFlow() {
        if (started) return
        started = true
        viewModelScope.launch {
            flow {
                for (i in 0..100) {
                    delay(100)
                    emit(i)
                }
            }
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.d(TAG, "error: $it")
                }.onCompletion {
                    started = false
                }
                .collect {
                    progress.value = it
                }
        }
    }
}