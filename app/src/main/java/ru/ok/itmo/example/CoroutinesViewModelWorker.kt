package ru.ok.itmo.example

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesViewModelWorker(
    private val counter: Counter
) : ViewModelWorker() {
    override val liveData = MutableLiveData(counter)
    override val active = MutableLiveData(false)

    override fun run(sleepTimeMs: Long)
    {
        CoroutineScope(Dispatchers.IO).launch {
            active.postValue(true)
            counter.initCounter()
            do
            {
                liveData.postValue(counter)
                delay(sleepTimeMs)
            } while (counter.updateCounter())
            active.postValue(false)
        }
    }
}