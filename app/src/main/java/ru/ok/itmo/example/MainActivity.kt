package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var buttonStart : Button
    private lateinit var progressBar : ProgressBar
    private var isButtonClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonStart = findViewById(R.id.startButton)
        progressBar = findViewById(R.id.progress_bar)

        buttonStart.setOnClickListener {
            if (!isButtonClicked) {
                getProcessThread().start()
            }
        }
    }


    private fun getProcessThread(): Thread {
        return Thread {
            isButtonClicked = true
            var currentProgress = 0
            if (progressBar.progress != 100) {
                currentProgress = progressBar.progress
            }
            for (i in currentProgress + 1..100) {
                runOnUiThread {
                    progressBar.progress = i
                }
                Thread.sleep(100)
            }
            isButtonClicked = false
        }
    }

}