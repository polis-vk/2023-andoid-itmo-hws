package ru.ok.itmo.example.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.example.R

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var btnStart: Button
    private lateinit var progressBar: ProgressBar
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStart = view.findViewById(R.id.start_button)
        progressBar = view.findViewById(R.id.progress_bar)

        btnStart.setOnClickListener {
            stateFlowSolution()
        }
    }

    private fun coroutinesSolution() {
        MainScope().launch(Dispatchers.IO) {
            for (i in 1..100) {
                withContext(Dispatchers.Main) {
                    progressBar.progress = i
                }
                delay(100)
            }
        }
    }

    private fun flowSolution() {
        MainScope().launch(Dispatchers.IO) {
            flow {
                for (i in 1..100) {
                    emit(i)
                    delay(100)
                }
            }.flowOn(Dispatchers.Main).collect {
                progressBar.progress = it
            }
        }
    }

    private fun stateFlowSolution() {
        viewModel.run().also {
            lifecycleScope.launch {
                viewModel.counter.collect() {
                    progressBar.progress = it
                }
            }
        }
    }
}