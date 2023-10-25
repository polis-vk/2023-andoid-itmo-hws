package ru.ok.itmo.example

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters


class AlarmWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val alarmManager: AlarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = PendingIntent.getBroadcast(
            applicationContext, 0, Intent(applicationContext, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        //Только для отрисовки, не использую читы))
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            60000,
            intent
        )
        return Result.success()
    }

}
