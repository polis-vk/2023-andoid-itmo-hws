package com.example.hw6

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion

class AlarmFlow(context: Context) {

    companion object
    {
        suspend fun scheduleAlarmWork(context: Context, timeInMillis: Long) {

            val delay_ = timeInMillis - System.currentTimeMillis()
            val fl : Flow<Unit> = flow {
                delay(delay_)
            }

            fl
                .flowOn(Dispatchers.IO)
                .onCompletion{ notify(context)}
                .collect{}
        }

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "channel_alarm"
                val descriptionText = "notification from WorkManager"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("channel_id", name, importance).apply {
                    description = descriptionText
                }

                val notificationManager =
                    context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }

        @SuppressLint("MissingPermission")
        private fun notify(context: Context) {

            createNotificationChannel(context)
            val notificationManager = NotificationManagerCompat.from(context)
            val notification = NotificationCompat.Builder(context, "channel_id")
                .setContentTitle("Будильник")
                .setContentText("Пора вставать!")
                .setSmallIcon(R.drawable.outline_notifications_active_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))

            notificationManager.notify(1, notification.build())

        }
    }

}