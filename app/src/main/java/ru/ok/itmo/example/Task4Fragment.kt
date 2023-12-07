package ru.ok.itmo.example

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels



class Task4Fragment : BaseTaskFragment() {

    private val viewModel: Task4ViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progress.observe(viewLifecycleOwner) { progress ->
            updateUi(progress)
        }
    }

    override fun configureUI() {
        task.text = getString(R.string.task_4)
    }

    override fun startWork() = viewModel.startWork()
}
