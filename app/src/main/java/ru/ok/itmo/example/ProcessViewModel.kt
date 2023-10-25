package ru.ok.itmo.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProcessViewModel : ViewModel() {
    val progress: MutableLiveData<Int> = MutableLiveData(0)

    suspend fun startProgress() {
        withContext(Dispatchers.IO) {
            launch {
                while (progress.value!! < 100) {
                    delay(100)
                    progress.postValue(progress.value!! + 1)
                }
            }
        }
    }
}
