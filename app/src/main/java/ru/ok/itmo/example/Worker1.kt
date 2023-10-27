package ru.ok.itmo.example

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters

class Worker1 (context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val dirtyDeedsDoneDirtCheap = inputData.getLong("time", -1);
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
        val intent = Intent(applicationContext, Reciever1::class.java);
        val penIntent = PendingIntent.getBroadcast(applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dirtyDeedsDoneDirtCheap, penIntent);
        return Result.success();
    }
}