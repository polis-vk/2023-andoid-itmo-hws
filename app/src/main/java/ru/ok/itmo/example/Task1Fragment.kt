package ru.ok.itmo.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Task1Fragment : BaseTaskFragment() {
    override fun configureUI() {
        task.text = getString(R.string.task_1)
    }

    override fun startWork() {
        MainScope().launch(Dispatchers.IO) {
            for (i in 0..100) {
                launch(Dispatchers.Main) {
                    updateUi(i)
                }
                delay(100)
            }
        }
    }
}
