package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RXProgressActivity : AppCompatActivity(R.layout.activity_rx_progress) {
    private var progressBar: ProgressBar? = null
    private var startButton: Button? = null
    private var process: Disposable? = null

    private var progress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startButton = findViewById(R.id.startThreadProcess)
        progressBar = findViewById(R.id.progressBar)

        startButton?.setOnClickListener { startProgressBar() }
    }

    private fun startProgressBar() {
        startButton?.isEnabled = false
        process = Observable.interval(100, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .take(100)
            .subscribe {
                progress++
                progressBar?.progress = progress
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        process?.dispose()
    }
}