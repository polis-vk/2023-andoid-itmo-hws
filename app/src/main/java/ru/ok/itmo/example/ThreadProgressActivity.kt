package ru.ok.itmo.example

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class ThreadProgressActivity : AppCompatActivity(R.layout.activity_thread_progress) {
    private var progressBar: ProgressBar? = null
    private var startButton: Button? = null
    private var resetButton: Button? = null
    private var speedRatio: RadioGroup? = null
    private var progress: Int = 0

    private var process: Thread? = null

    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startButton = findViewById(R.id.startThreadProcess)
        resetButton = findViewById(R.id.resetThreadProcess)
        progressBar = findViewById(R.id.progressBar)
        speedRatio = findViewById(R.id.speedRatio)

        startButton?.setOnClickListener { startProgressBar() }
        resetButton?.setOnClickListener { resetProcessBar() }
    }

    override fun onDestroy() {
        super.onDestroy()
        process?.interrupt()
        process?.join(100)
    }

    private fun startProgressBar() {
        startButton?.isEnabled = false
        val sleep = getSelectedSpeed()

        process = Thread {
            try {
                while (progress < 100) {
                    Thread.sleep(sleep)
                    handler.post {
                        progress++
                        progressBar?.progress = progress
                    }
                }
            } catch (ignore: InterruptedException) {
                handler.post {
                    resetButton?.isEnabled = true
                }
            } finally {
                handler.post {
                    progressBar?.progress = progress
                    resetButton?.isEnabled = true
                }
            }
        }
        process?.start()
    }

    private fun resetProcessBar() {
        progress = 0
        progressBar?.progress = progress
        startButton?.isEnabled = true
        resetButton?.isEnabled = false
    }

    private fun getSelectedSpeed(): Long {
        val selectedId = speedRatio?.checkedRadioButtonId ?: -1
        val selectedRatio = findViewById<RadioButton>(selectedId) ?: return 100
        return selectedRatio.text.toString().replace("ms", "").toLong()
    }
}