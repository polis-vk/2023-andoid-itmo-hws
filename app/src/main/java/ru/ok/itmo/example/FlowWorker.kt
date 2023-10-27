package ru.ok.itmo.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FlowWorker(
    private val counter: Counter
): Worker<Flow<Counter>>  {
    override fun run(sleepTimeMs: Long): Flow<Counter> = flow {
        counter.initCounter()
        do
        {
            emit(counter)
            delay(sleepTimeMs)
        } while (counter.updateCounter())
    }.flowOn(Dispatchers.IO)
}