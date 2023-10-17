package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var step: Long = 0
    private var flag = false
    private var now = 0
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val start = findViewById<Button>(R.id.start_button)
        val end = findViewById<Button>(R.id.end_button)
        val buttons = findViewById<RadioGroup>(R.id.radioGroup)
        val progress = findViewById<ProgressBar>(R.id.progress_bar)

        class ProgressBarUpdater : Runnable {
            override fun run() {
                if (step.toInt() == 0) {
                    step = 100
                }
                while (now < 100 && !flag) {
                    Thread.sleep(step)
                    now++
                    progress.progress = now
                }
            }
        }

        fun startProgressThread() {
            val thread = Thread(ProgressBarUpdater())
            thread.start()
        }

        fun startProgress() {
            if (step.toInt() == 0) {
                step = 100
            }
            if (disposable == null || disposable?.isDisposed == true) {
                disposable = Observable.interval(step, TimeUnit.MILLISECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .takeWhile { !flag && now < 100 }
                    .subscribe {
                        now++
                        progress.progress = now
                    }
            }
        }

        buttons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton4 -> step = 50
                R.id.radioButton3 -> step = 100
                R.id.radioButton -> step = 300
                R.id.radioButton2 -> step = 500
            }
        }

        start.setOnClickListener {
            flag = false
            startProgress()
        }

        end.setOnClickListener {
            flag = true
            now = 0
            progress.progress = 0
            disposable?.dispose()
        }
    }
}
