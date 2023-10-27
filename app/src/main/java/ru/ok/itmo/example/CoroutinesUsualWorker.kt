package ru.ok.itmo.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutinesUsualWorker(
    private val counter: Counter,
    private val startUpdateUi: () -> Unit,
    private val updateUi: (value: Int) -> Unit,
    private val endUpdateUi: () -> Unit
    ) : Worker<Unit> {

    override fun run(sleepTimeMs: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                startUpdateUi()
            }
            counter.initCounter()
            do
            {
                withContext(Dispatchers.Main) {
                    updateUi(counter.value)
                }
                delay(sleepTimeMs)
            } while (counter.updateCounter())
            withContext(Dispatchers.Main) {
                endUpdateUi()
            }
        }
    }
}