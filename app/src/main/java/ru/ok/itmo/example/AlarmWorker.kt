package ru.ok.itmo.example

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.util.concurrent.TimeUnit

class AlarmWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            showNotification()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        createNotificationChannel()
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_notifications_active_24)
            .setContentTitle("Alarm")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()
        NotificationManagerCompat.from(context).notify(1, notification)
    }

    private fun createNotificationChannel() {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_alarm"

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
        fun scheduleAlarmWork(context: Context, timeInMillis: Long) {
            val delay = timeInMillis - System.currentTimeMillis()
            val alarmRequest = OneTimeWorkRequest.Builder(AlarmWorker::class.java)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()
            WorkManager.getInstance(context).enqueue(alarmRequest)
        }
    }

}