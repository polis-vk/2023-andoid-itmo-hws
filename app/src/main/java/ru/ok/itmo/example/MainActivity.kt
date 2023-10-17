package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var step: Long = 0
    private var flag = false
    private var now = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val start = findViewById<Button>(R.id.start_button)
        val end = findViewById<Button>(R.id.end_button)
        val buttons = findViewById<RadioGroup>(R.id.radioGroup)
        val progress = findViewById<ProgressBar>(R.id.progress_bar)

        class ProgressBarUpdater : Runnable {
            override fun run() {
                if (step.toInt() == 0) {
                    step = 100
                }
                while (now < 100 && !flag) {
                    Thread.sleep(step)
                    now++
                    progress.progress = now
                }
            }
        }

        fun startProgressThread() {
            val thread = Thread(ProgressBarUpdater())
            thread.start()
        }

        buttons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton4 -> step = 50
                R.id.radioButton3 -> step = 100
                R.id.radioButton -> step = 300
                R.id.radioButton2 -> step = 500
            }
        }

        start.setOnClickListener {
            startProgressThread()
            flag = false
        }

        end.setOnClickListener {
            flag = true
            now = 0
            progress.progress = 0
        }
    }
}
