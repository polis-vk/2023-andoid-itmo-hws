package ru.ok.itmo.example

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TimePicker
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var inc = 0;
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar);
        val corButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.fill_button);
        val flowButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.restart_button);
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group);
        val alarmButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.alarm_button);

        fun coroutinesSolution(){
            progressBar.progress = 0;
            inc = 0;
            val interval: Long;
            when (radioGroup.checkedRadioButtonId){
                R.id.button_50 -> interval = 50
                R.id.button_100 -> interval = 100
                R.id.button_300 -> interval = 300
                R.id.button_500 -> interval = 500
                else -> interval = 100
            }

            CoroutineScope(Dispatchers.IO).launch{
                while(progressBar.progress != 100) {
                    delay(interval);
                    inc++;
                    CoroutineScope(Dispatchers.Main).launch {
                        progressBar.progress = inc;
                    }
                }
            }
        }

        fun flowSolution(){
            progressBar.progress = 0;
            inc = 0;
            val interval: Long;
            when (radioGroup.checkedRadioButtonId){
                R.id.button_50 -> interval = 50
                R.id.button_100 -> interval = 100
                R.id.button_300 -> interval = 300
                R.id.button_500 -> interval = 500
                else -> interval = 100
            }

            val ioFlow = flow {
                delay(interval)
                inc++
                emit(inc)
            }.flowOn(Dispatchers.IO)

            CoroutineScope(Dispatchers.Main).launch{
                while(progressBar.progress != 100){
                    ioFlow.collect {
                        progressBar.progress = inc;
                    }
                }
            }


        }

        corButton.setOnClickListener {
            coroutinesSolution();
        }
        flowButton.setOnClickListener {
            flowSolution()
        }
        alarmButton.setOnClickListener {
            val calendar = Calendar.getInstance();
            val timeSetListener = TimePickerDialog.OnTimeSetListener{view: TimePicker?, hourOfDay: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                val data = Data.Builder();
                data.putLong("time", calendar.timeInMillis)
                val alarmJob = OneTimeWorkRequest.Builder(Worker1::class.java)
                    .setInputData(data.build())
                    .build()
                WorkManager.getInstance(this).enqueue(alarmJob);
            }
            TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }
    }
}