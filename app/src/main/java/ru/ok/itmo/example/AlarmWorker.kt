package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

import android.app.NotificationChannel
import android.app.NotificationManager

import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getString
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit


class AlarmWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun showNotification() {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(applicationContext, "channel_id")
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(getString(applicationContext, R.string.alarm))
            .setContentText(getString(applicationContext, R.string.alarm_message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alarm Channel"
            val descriptionText = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        fun setAlarm(context: Context, hour: Int, minute: Int) {
            val currentTime = Calendar.getInstance()
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, hour)
            selectedTime.set(Calendar.MINUTE, minute)

            if (selectedTime.before(currentTime)) {
                selectedTime.add(Calendar.DATE, 1)
            }

            val delay = selectedTime.timeInMillis - currentTime.timeInMillis

            val inputData = Data.Builder()
                .putLong("delay", delay)
                .build()

            val workRequest = OneTimeWorkRequest.Builder(AlarmWorker::class.java)
                .setInputData(inputData)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}
