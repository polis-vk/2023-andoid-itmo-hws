package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer


class Task4Fragment : BaseTaskFragment() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progress.observe(viewLifecycleOwner) { progress ->
            updateUi(progress)
        }
    }

    override fun configureUI() {
        task.text = getString(R.string.task_4)
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
