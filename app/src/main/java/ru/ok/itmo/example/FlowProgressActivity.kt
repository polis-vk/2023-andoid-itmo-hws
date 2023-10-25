package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FlowProgressActivity : AppCompatActivity(R.layout.activity_flow_progress) {
    private var progressBar: ProgressBar? = null
    private var startButton: Button? = null
    private var progress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = findViewById(R.id.progressBar)
        startButton = findViewById(R.id.startProcess)

        startButton?.setOnClickListener { launchProcess() }
    }

    private fun launchProcess() {
        val progressFlow = flow {
            while (progress < 100) {
                delay(100)
                progress++
                emit(progress)
            }
        }.flowOn(Dispatchers.IO)

        MainScope().launch(Dispatchers.Main) {
            startButton?.isEnabled = false
            progressFlow.collect { progressBar?.progress = it }
        }
    }
}