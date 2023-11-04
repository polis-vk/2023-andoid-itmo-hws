package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import ru.ok.itmo.example.Timers.CoroutinesTimer
import ru.ok.itmo.example.Timers.FlowTimer
import ru.ok.itmo.example.Timers.ObservableTimer
import ru.ok.itmo.example.Timers.StateFlowTimer
import ru.ok.itmo.example.Timers.ThreadTimer
import ru.ok.itmo.example.Timers.TimerProtocol

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var sleepTime = 100L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        val startButton = findViewById<Button>(R.id.start_button)
        val progress = findViewById<ProgressBar>(R.id.progress_bar)
        progress.max = sleepTime.toInt()

        var timer: TimerProtocol = ThreadTimer(sleepTime)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            timer.reset()
            progress.progress = 0
            timer = when (checkedId) {
                R.id.thread -> ThreadTimer(sleepTime)
                R.id.observable -> ObservableTimer(sleepTime)
                R.id.coroutines -> CoroutinesTimer(sleepTime)
                R.id.flow -> FlowTimer(sleepTime)
                R.id.state_flow -> StateFlowTimer(sleepTime)
                else -> ThreadTimer(sleepTime)
            }
        }

        startButton.setOnClickListener {
            timer.reset()
            val completion = { time: Int -> progress.progress = time }
            timer.run(completion)
        }
    }
}
