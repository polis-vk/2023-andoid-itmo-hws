package ru.ok.itmo.example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val threadProgressButton: Button = findViewById(R.id.threadProgress)
        val rxProgressButton: Button = findViewById(R.id.rxProgress)

        threadProgressButton.setOnClickListener { toThreadProgressActivity() }
        rxProgressButton.setOnClickListener { toRxProgressActivity() }
    }

    private fun toThreadProgressActivity() {
        startActivity(Intent(this, ThreadProgressActivity::class.java))
    }

    private fun toRxProgressActivity() {
        startActivity(Intent(this, RXProgressActivity::class.java))
    }
}