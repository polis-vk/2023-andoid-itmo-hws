package ru.ok.itmo.example.alarm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.ok.itmo.example.R
import java.util.concurrent.TimeUnit

class AlarmWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        return try {
            createNotificationChannel()
            val notificationManager = NotificationManagerCompat.from(applicationContext)

            val notification = NotificationCompat.Builder(applicationContext, "channel_id")
                .setContentTitle(inputData.getString(TITLE_KEY))
                .setContentText(inputData.getString(TEXT_KEY))
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))

            notificationManager.notify(1, notification.build())
            return Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_alarm"
            val descriptionText = "notification from WorkManager"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val TITLE_KEY = "title"
        const val TEXT_KEY = "text"
        fun scheduleAlarmWork(context: Context, delay: Long, title: String, text: String) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresBatteryNotLow(false)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setRequiresStorageNotLow(false)
                .build()

            val data = Data.Builder()
                .putString(TITLE_KEY, title)
                .putString(TEXT_KEY, text)
                .build()

            val alarmRequest = OneTimeWorkRequest.Builder(AlarmWorker::class.java)
                .setConstraints(constraints)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()
            WorkManager.getInstance(context).enqueue(alarmRequest)
        }
    }
}