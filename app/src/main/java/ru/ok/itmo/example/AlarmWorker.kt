package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class AlarmWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        return try {
            createNotificationChannel()
            val notificationManager = NotificationManagerCompat.from(applicationContext)
            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle(ContextCompat.getString(applicationContext, R.string.alarm_title))
                .setContentText(ContextCompat.getString(applicationContext, R.string.alarm_content))
                .setSmallIcon(androidx.core.R.drawable.ic_call_answer)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notificationManager.notify(System.currentTimeMillis().toInt(), notification.build())
            return Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_alarm"
        private const val CHANNEL_DESCRIPTION = "Уведомление от будильника"
        fun scheduleAlarmWork(context: Context, timeInMillis: Long) {
            val delay = timeInMillis - System.currentTimeMillis()

            val alarmRequest = OneTimeWorkRequest.Builder(AlarmWorker::class.java)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueue(alarmRequest)
        }
    }
}