package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxActivity : AppCompatActivity(R.layout.activity_action) {

    private lateinit var buttonStart: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var testTextView: TextView

    private var isButtonClicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonStart = findViewById(R.id.startButton)
        progressBar = findViewById(R.id.progress_bar)
        testTextView = findViewById(R.id.test_tv)

        buttonStart.setOnClickListener {
            if (!isButtonClicked) {
                isButtonClicked = true
                val disposable = Observable.intervalRange(
                    1, 100, 0, 100, TimeUnit.MILLISECONDS, Schedulers.io()
                ).doOnComplete { isButtonClicked = false }
                    .observeOn(Schedulers.newThread())
                    .subscribe {
                        runOnUiThread {
                            progressBar.progress = it.toInt()
                            testTextView.text = it.toString()
                        }

                    }
            }
        }
    }
}