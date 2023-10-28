package ru.ok.itmo.example.alarm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.ok.itmo.example.R
import java.util.UUID
import java.util.concurrent.TimeUnit

class AlarmWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        return try {
            createNotificationChannel()
            val notificationManager = NotificationManagerCompat.from(applicationContext)
            val notification = NotificationCompat.Builder(applicationContext, "channel_id")
                .setContentTitle("Alarm!")
                .setContentText("alarm alarm alarm...")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_MAX)

            notificationManager.notify(1, notification.build())
            return Result.success()
        } catch (e: Exception) {
            Log.d("debug", e.message.toString())
            Result.failure()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "channel_alarm"
        val descriptionText = "Alarm notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }

        val notificationManager =
            applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {

        fun scheduleAlarmWork(context: Context, timeInMillis: Long): UUID {
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
            return alarmRequest.id
        }
    }
}
