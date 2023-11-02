package ru.ok.itmo.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ok.itmo.example.R

class StateFlowActivity : AppCompatActivity(R.layout.progress) {

    private val viewModel by viewModels<StateFlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.startButton).setOnClickListener {
            viewModel.start()
        }
        val bar = findViewById<ProgressBar>(R.id.progressBar)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.stateFlow.collect {
                bar.progress = it
                if (it == bar.max) {
                    Toast.makeText(
                        this@StateFlowActivity,
                        "StateFlow Completed!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}