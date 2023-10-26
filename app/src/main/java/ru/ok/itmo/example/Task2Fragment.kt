package ru.ok.itmo.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Task2Fragment : BaseTaskFragment() {
    override fun configureUI() {
        task.text = getString(R.string.task_2)
    }

    override fun startWork() {
        val progressFlow = flow {
            for (i in 0..100) {
                emit(i)
                delay(100)
            }
        }

        MainScope().launch(Dispatchers.IO) {
            progressFlow
                .flowOn(Dispatchers.IO)
                .collect { n ->
                    launch(Dispatchers.Main) {
                        updateUi(n)
                    }
                }
        }
    }
}
