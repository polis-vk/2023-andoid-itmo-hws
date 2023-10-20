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
    private var timer: Long = 0
    private var processRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.min = 0
        progressBar.max = 100

        val startButton: Button = findViewById(R.id.startButton)
        val startButtonRx: Button = findViewById(R.id.startButtonRx)
        val resetButton: Button = findViewById(R.id.resetButton)

        val radioGroup: RadioGroup = findViewById(R.id.millisGroup)
        //Task 3
        radioGroup.setOnCheckedChangeListener { _, item ->
            when (item) {
                R.id.m50 -> timer = 50
                R.id.m100 -> timer = 100
                R.id.m300 -> timer = 300
                R.id.m500 -> timer = 500
            }
        }

        radioGroup.check(R.id.m100)

        fun step() {
            if ( progressBar.progress + 1 >= progressBar.max ) {
                processRunning = false
            }

            runOnUiThread { progressBar.progress++ }
        }
        //Task 1
        fun useThreadAndRunnable() {
            thread(true) {
                while (processRunning) {
                    step()
                    Thread.sleep(timer)
                }
            }
        }
        //Task 2
        fun useRxJava() {
            Observable
                .interval(timer, TimeUnit.MILLISECONDS)
                .takeWhile { processRunning }
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe { step() }
        }

        startButton.setOnClickListener {
            processRunning = true
            useThreadAndRunnable()
        }
        startButtonRx.setOnClickListener {
            processRunning = true
            useRxJava()
        }
        resetButton.setOnClickListener {
            processRunning = false
            runOnUiThread { progressBar.progress = progressBar.min }
        }
    }
}