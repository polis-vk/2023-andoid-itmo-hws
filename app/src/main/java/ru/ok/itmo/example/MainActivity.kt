package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var timeout: Long = 0
    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.min = 0
        progressBar.max = 100

        findViewById<RadioGroup>(R.id.millis).setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.m50 -> timeout = 50
                R.id.m100 -> timeout = 100
                R.id.m300 -> timeout = 300
                R.id.m500 -> timeout = 500
            }
        }

        findViewById<RadioGroup>(R.id.millis).check(R.id.m100)

        fun step() {
            if (progressBar.progress + 1 >= progressBar.max) {
                running = false
            }

            runOnUiThread { progressBar.progress++ }
        }

        fun useThreadAndRunnable() {
            thread(true) {
                while (running) {
                    step()
                    Thread.sleep(timeout)
                }
            }
        }

        fun useRxJava() {
            Observable.interval(timeout, TimeUnit.MILLISECONDS).takeWhile { running }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { step() }
        }

        findViewById<Button>(R.id.start).setOnClickListener {
            running = true
            useThreadAndRunnable()
        }

        findViewById<Button>(R.id.reset).setOnClickListener {
            running = false
            runOnUiThread { progressBar.progress = progressBar.min }
        }
    }
}
