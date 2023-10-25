package ru.ok.itmo.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val coroutinesButton: Button = findViewById(R.id.coroutinesProgress)
        val flowButton: Button = findViewById(R.id.flowProgress)
        val alarmButton: Button = findViewById(R.id.alarmTask)
        val liveDataButton: Button = findViewById(R.id.liveDataProgress)

        coroutinesButton.setOnClickListener { toCoroutinesProgressActivity() }
        flowButton.setOnClickListener { toFlowProgressActivity() }
        alarmButton.setOnClickListener { toAlarmActivity() }
        liveDataButton.setOnClickListener { toLiveDataActivity() }
    }

    private fun toCoroutinesProgressActivity() {
        startActivity(Intent(this, CoroutinesProgressActivity::class.java))
    }

    private fun toFlowProgressActivity() {
        startActivity(Intent(this, FlowProgressActivity::class.java))
    }

    private fun toLiveDataActivity() {
        startActivity(Intent(this, LiveDataProgressActivity::class.java))
    }

    private fun toAlarmActivity() {
        startActivity(Intent(this, AlarmActivity::class.java))
    }
}