package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var sleepTime = 100L

    enum class State {
        THREAD, OBSERVABLE
    }

    private var state = State.THREAD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        val startButton = findViewById<Button>(R.id.start_button)
        val resetButton = findViewById<Button>(R.id.reset_button)
        val threadButton = findViewById<Button>(R.id.thread_button)
        val observableButton = findViewById<Button>(R.id.observable_button)
        val progress = findViewById<ProgressBar>(R.id.progress_bar)
        progress.max = sleepTime.toInt()
        resetButton.isEnabled = false

        val timer = Timer(sleepTime)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            sleepTime = when(checkedId) {
                R.id.time_50 -> 50
                R.id.time_100 -> 100
                R.id.time_300 -> 300
                R.id.time_500 -> 500
                else -> 100
            }
        }

        startButton.setOnClickListener {
            val completion = { time: Int -> progress.progress = time }
            timer.sleepTime = sleepTime
            when (state) {
                State.THREAD -> {
                    timer.threadRunner(completion)
                }
                State.OBSERVABLE -> {
                    timer.observableRunner(completion)
                }
            }
        }

        resetButton.setOnClickListener {
            timer.reset()
            progress.progress = 0
        }

        threadButton.setOnClickListener {
            state = State.THREAD
            resetButton.isEnabled = false
            progress.progress = 0
            timer.reset()
        }

        observableButton.setOnClickListener {
            state = State.OBSERVABLE
            resetButton.isEnabled = true
            progress.progress = 0
            timer.reset()
        }
    }
}
