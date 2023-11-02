package ru.ok.itmo.example.alarm

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class AlarmWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d("ALARM", "Success alarm")
        return Result.success()
    }
}