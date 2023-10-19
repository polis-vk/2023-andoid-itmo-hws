package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var startButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var timeRadioGroup: RadioGroup
    private var updateInterval: Long = 100
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startButton = findViewById(R.id.startButton)
        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)

        timeRadioGroup = findViewById(R.id.timeRadioGroup)

        timeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio50 -> updateInterval = 50
                R.id.radio100 -> updateInterval = 100
                R.id.radio300 -> updateInterval = 300
                R.id.radio500 -> updateInterval = 500
            }
        }

        startButton.setOnClickListener {
            startWorkRx()
        }
    }

    private fun startWork() {
        startButton.isEnabled = false

        val thread = Thread {
            val fixedUpdateInterval = updateInterval
            for (i in 0..100) {
                runOnUiThread {
                    updateUi(i)
                }
                try {
                    Thread.sleep(fixedUpdateInterval)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        thread.start()
    }

    private fun startWorkRx() {
        startButton.isEnabled = false

        disposable = Observable.interval(updateInterval, TimeUnit.MILLISECONDS)
            .takeWhile { it <= 100 }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { n ->
                updateUi(n.toInt())
            }
    }

    private fun updateUi(n: Int) {
        progressBar.progress = n
        progressText.text = "$n"
        if (n == 100) {
            startButton.isEnabled = true
            startButton.text = getString(R.string.restart)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
