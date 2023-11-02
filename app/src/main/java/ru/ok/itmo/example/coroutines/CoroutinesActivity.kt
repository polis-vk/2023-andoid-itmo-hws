package ru.ok.itmo.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.example.R

class CoroutinesActivity : AppCompatActivity(R.layout.progress) {

    private var timerJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bar = findViewById<ProgressBar>(R.id.progressBar)
        findViewById<Button>(R.id.startButton).setOnClickListener {
            start(bar)
        }
    }

    private fun start(progressBar: ProgressBar) {
        timerJob?.cancel()
        timerJob = lifecycleScope.launch(Dispatchers.Main) {
            progressBar.progress = 0
            withContext(Dispatchers.IO) {
                while (progressBar.progress < progressBar.max) {
                    delay(100)
                    withContext(Dispatchers.Main) {
                        progressBar.progress += 1
                    }
                }
            }
            Toast.makeText(this@CoroutinesActivity, "Coroutines Completed!", Toast.LENGTH_LONG).show()
        }
    }
}