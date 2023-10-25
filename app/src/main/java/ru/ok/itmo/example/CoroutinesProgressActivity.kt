package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesProgressActivity : AppCompatActivity(R.layout.activity_coroutines_progress) {
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
        startButton?.isEnabled = false
        MainScope().launch(Dispatchers.IO) {
            while (progress < 100) {
                delay(100)
                progress++
                launch(Dispatchers.Main) {
                    progressBar?.progress = progress
                }
            }
        }
    }
}