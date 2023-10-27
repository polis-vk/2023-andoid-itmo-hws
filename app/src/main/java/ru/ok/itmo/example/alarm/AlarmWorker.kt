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
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.ok.itmo.example.R
import java.util.concurrent.TimeUnit

class AlarmWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        return try {
            createNotificationChannel()
            val notificationManager = NotificationManagerCompat.from(applicationContext)
            val notification = NotificationCompat.Builder(applicationContext, "channel_id")
                .setContentTitle("Alarm!")
                .setContentText("alarm alarm alarm...")
                .setSmallIcon(R.drawable.ic_launcher_background)
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

        fun scheduleAlarmWork(context: Context, timeInMillis: Long) {
            val constraints = Constraints.Builder()
                .build()

            val inputData = Data.Builder()
                .putLong("alarm_time", timeInMillis)
                .build()

            val delay = timeInMillis - System.currentTimeMillis()

            val alarmRequest = OneTimeWorkRequest.Builder(AlarmWorker::class.java)
                .setConstraints(constraints)
                .setInputData(inputData)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .addTag("alarm_tag")
                .build()

            WorkManager.getInstance(context).enqueue(alarmRequest)
        }
    }
}
