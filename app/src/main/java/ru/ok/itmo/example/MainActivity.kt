package ru.ok.itmo.example

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import ru.ok.itmo.example.timers.CoroutinesTimer
import ru.ok.itmo.example.timers.FlowTimer
import ru.ok.itmo.example.timers.ObservableTimer
import ru.ok.itmo.example.timers.StateFlowTimer
import ru.ok.itmo.example.timers.ThreadTimer
import ru.ok.itmo.example.timers.TimerProtocol
import ru.ok.itmo.example.alarm.AlarmWorker
import java.util.Calendar

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var sleepTime = 100L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        val startButton = findViewById<Button>(R.id.start_button)
        val progress = findViewById<ProgressBar>(R.id.progress_bar)
        val alarmButton = findViewById<Button>(R.id.alarm_button)

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

        alarmButton.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private var hourOfDay = 0
    private var minute = 0

    private fun showTimePickerDialog() {
        val c = Calendar.getInstance()
        hourOfDay = c.get(Calendar.HOUR_OF_DAY)
        minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { timePicker: TimePicker, hourOfDay: Int, minute: Int ->
                setAlarm(hourOfDay, minute)
            },
            hourOfDay,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun setAlarm(hour: Int, minute: Int) {
        val now = Calendar.getInstance()
        val alarmTime = Calendar.getInstance()
        alarmTime[Calendar.HOUR_OF_DAY] = hour
        alarmTime[Calendar.MINUTE] = minute

        if (alarmTime.before(now)) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1)
        }

        AlarmWorker.scheduleAlarmWork(this@MainActivity, alarmTime.timeInMillis)
    }
}
