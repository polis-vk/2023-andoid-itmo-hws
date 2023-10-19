package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    lateinit var button: Button
    lateinit var progressBar: ProgressBar
    lateinit var resetButton: Button
    lateinit var radioGroup: RadioGroup
    lateinit var switchRX: Switch

    var progressThread: Thread? = null
    var progressObservable: Disposable? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        button = findViewById(R.id.button)
        progressBar = findViewById(R.id.progressBar)
        resetButton = findViewById(R.id.resetButton)
        radioGroup = findViewById(R.id.radioGroup)
        switchRX = findViewById(R.id.switchRX)
        radioGroup.check(R.id.radioButton2)

        button.setOnClickListener {
            val delay = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                .text.toString().toInt()

            button.isEnabled = false
            radioGroup.setEnabledFull(false)

            if (switchRX.isChecked) {
                progressObservable = Observable
                    .intervalRange(1, 100, 0, delay.toLong(), TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe {
                        progressBar.progress = it.toInt()
                    }
            } else {
                progressThread = Thread(IncreaseCount(progressBar, delay)).apply { start() }
            }
        }

        resetButton.setOnClickListener {
            val finalProgressThread = progressThread
            if (finalProgressThread is Thread) {
                finalProgressThread.interrupt()
                try {
                    finalProgressThread.join()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            val finalProgressObservable = progressObservable
            if (finalProgressObservable is Disposable) {
                finalProgressObservable.dispose()
            }
            progressBar.progress = 0
            button.isEnabled = true
            radioGroup.setEnabledFull(true)
        }
    }

    private fun RadioGroup.setEnabledFull(value: Boolean) {
        for (child in this.children) {
            child.isEnabled = value
        }
    }

    class IncreaseCount(
        val progressBar: ProgressBar,
        val delay: Int
    ): Runnable {
        override fun run() {
            try {
                while (!Thread.interrupted() && progressBar.progress < 100) {
                    progressBar.progress++
                    if (progressBar.progress < 100) {
                        Thread.sleep(delay.toLong())
                    }
                }
            } catch (e: InterruptedException) {
                // stop thread
            }
        }
    }
}