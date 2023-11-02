package ru.ok.itmo.example

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.ok.itmo.example.alarm.AlarmWorker
import ru.ok.itmo.example.coroutines.CoroutinesActivity
import ru.ok.itmo.example.flow.FlowActivity
import ru.ok.itmo.example.stateflow.StateFlowActivity
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.flow).setOnClickListener {
            startActivity(Intent(this, FlowActivity::class.java))
        }
        findViewById<Button>(R.id.coroutines).setOnClickListener {
            startActivity(Intent(this, CoroutinesActivity::class.java))
        }
        findViewById<Button>(R.id.stateFlow).setOnClickListener {
            startActivity(Intent(this, StateFlowActivity::class.java))
        }
        findViewById<Button>(R.id.alarm).setOnClickListener { setAlarm() }
    }

    private fun setAlarm() {
        val (hour, minute) = Calendar.getInstance()
            .let { it.get(Calendar.HOUR_OF_DAY) to it.get(Calendar.MINUTE) }

        TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val selectedTime = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMinute)
                    set(Calendar.SECOND, 0)
                }


                WorkManager.getInstance(this@MainActivity).enqueue(
                    OneTimeWorkRequest.Builder(AlarmWorker::class.java)
                        .setConstraints(
                            Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                                .setRequiresCharging(false)
                                .setRequiresBatteryNotLow(false)
                                .setRequiresStorageNotLow(false)
                                .setRequiresDeviceIdle(false)
                                .build()
                        )
                        .setInitialDelay(
                            selectedTime.timeInMillis - Calendar.getInstance().timeInMillis,
                            TimeUnit.MILLISECONDS
                        )
                        .build()
                )
            },
            hour, minute, true
        ).show()
    }
}