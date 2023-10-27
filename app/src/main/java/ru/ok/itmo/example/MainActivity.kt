package ru.ok.itmo.example

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.Calendar

class MainActivity : AppCompatActivity(R.layout.activity_main), TimePickerDialog.OnTimeSetListener {
    private lateinit var progressViewModel: ProgressViewModel
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressViewModel = ViewModelProvider(this)[ProgressViewModel::class.java]
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.min = 0
        progressBar.max = 100

        fun useCoroutines() =
            MainScope().launch(Dispatchers.IO) {
                generateSequence(0) { if (it < progressBar.max) it + 1 else null }.forEach {
                    delay(100)
                    withContext(Dispatchers.Main) { progressBar.progress = it }
                }
            }

        fun useFlow() =
            MainScope().launch(Dispatchers.Main) {
                flow {
                    repeat(progressBar.max + 1) {
                        delay(100)
                        emit(it)
                    }
                }.flowOn(Dispatchers.IO).collect { progressBar.progress = it }
            }

        fun useStateFlow() =
            progressViewModel.run(progressBar.max).also {
                lifecycleScope.launch {
                    progressViewModel.progress.collect {
                        progressBar.progress = it
                    }
                }
            }

        findViewById<Button>(R.id.start).setOnClickListener {
            job?.cancel()
            job = useStateFlow()
        }

        findViewById<Button>(R.id.alarm).setOnClickListener {
            val picker = TimePickerFragment()
            picker.show(supportFragmentManager, "timePicker")
        }
    }

    override fun onTimeSet(view: TimePicker, hours: Int, minutes: Int) {
        fun error(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        val (nowH, nowM) = Calendar.getInstance().run { Pair(get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE)) }

        when {
            hours < 0 || minutes < 0 -> error("Invalid input $hours:$minutes")
            hours < nowH || (hours == nowH && minutes < nowM) ->
                error("You can not select time in the past $hours:$minutes")
        }

        val time = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        OneTimeWorkRequest.Builder(AlarmWorker::class.java)
            .setConstraints(Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(false)
                .setRequiresStorageNotLow(false)
                .setRequiresDeviceIdle(false)
                .build())
            .setInitialDelay(Duration.ZERO)
            .setInputData(Data.Builder().putLong("millis", time).build())
            .build()
            .run { WorkManager.getInstance(this@MainActivity).enqueue(this) }
    }
}
