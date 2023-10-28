package ru.ok.itmo.example.alarm

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel

class AlarmViewModel : ViewModel() {
    fun getPeriod(hours: Int, minutes: Int): Long {
        val now = Calendar.getInstance()
        val alarmTime = Calendar.getInstance()
        alarmTime[Calendar.HOUR_OF_DAY] = hours
        alarmTime[Calendar.MINUTE] = minutes

        if (alarmTime.before(now)) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1)
        }
        return alarmTime.timeInMillis - System.currentTimeMillis()
    }
}