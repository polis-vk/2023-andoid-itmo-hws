package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class AlarmWorker(private val context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    @SuppressLint("ScheduleExactAlarm")
    override fun doWork(): Result = kotlin.runCatching {
        val millis = inputData.getLong("millis", System.currentTimeMillis())

        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = PendingIntent.getBroadcast(
            applicationContext, millis.hashCode(), Intent(applicationContext, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, intent)

        Result.success()
    }.getOrElse { Result.failure() }

    private class AlarmReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "Time out", Toast.LENGTH_SHORT).show()
        }
    }
}
