package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.time.Instant
import java.util.Calendar
import java.util.concurrent.TimeUnit


class AlarmActivity : AppCompatActivity(R.layout.activity_alarm) {
    private var createButton: Button? = null

    private var dateAndTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createButton = findViewById(R.id.createAlarm)
        createButton?.setOnClickListener { createButtonOnClick() }
    }

    private fun createButtonOnClick() {
        setDateTime()
    }

    private fun setDateTime() {
        val dateListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            dateAndTime[Calendar.YEAR] = year
            dateAndTime[Calendar.MONTH] = monthOfYear
            dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
            setTime()
        }
        DatePickerDialog(
            this, dateListener,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("NewApi")
    private fun setTime() {
        val timeListener = OnTimeSetListener { _, hour, minute ->
            dateAndTime[Calendar.HOUR] = hour
            dateAndTime[Calendar.MINUTE] = minute
            if (dateAndTime.toInstant().isBefore(Instant.now())) {
                Toast.makeText(this, "Alarm not added to past", Toast.LENGTH_LONG).show()
            } else {
                scheduleAlarm()
                Toast.makeText(this, "Alarm success added", Toast.LENGTH_LONG).show()
            }
        }
        TimePickerDialog(
            this, timeListener,
            dateAndTime.get(Calendar.HOUR),
            dateAndTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    @SuppressLint("NewApi")
    private fun scheduleAlarm() {

        val constraints = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(false)
            .setRequiresStorageNotLow(false)
            .setRequiresDeviceIdle(false)
            .build()

        val alarmRequest = OneTimeWorkRequest.Builder(AlarmWorker::class.java)
            .setConstraints(constraints)
            .setInitialDelay(
                dateAndTime.toInstant().toEpochMilli() - Instant.now().toEpochMilli(),
                TimeUnit.MILLISECONDS
            )
            .build()
        WorkManager.getInstance(this).enqueue(alarmRequest)
    }
}
