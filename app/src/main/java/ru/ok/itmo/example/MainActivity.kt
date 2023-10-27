package ru.ok.itmo.example

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var startButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startButton = findViewById(R.id.startButton)
        progressBar = findViewById(R.id.progressBar)
        radioGroup = findViewById(R.id.radioGroup)

        fun workWithUI() {
            progressBar.progress++
            if (progressBar.progress == 100) {
                startButton.visibility = View.VISIBLE
                startButton.text = getString(R.string.restartBarBtn)
            }
        }

        fun getDelayValue(): Long {
            val radioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            return when (radioButton.text) {
                "50ms" -> 50
                "100ms" -> 100
                "300ms" -> 300
                "500ms" -> 500
                else -> throw UnknownError("Неизвестное значение radioBtn")
            }
        }

        fun task1() {
            Thread {
                val delay = getDelayValue()
                while (progressBar.progress != 100) {
                    runOnUiThread { workWithUI() }
                    try {
                        Thread.sleep(delay)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }.start()
        }

        fun task2() {
            Observable.interval(getDelayValue(), TimeUnit.MILLISECONDS)
                .takeWhile { progressBar.progress != 100 }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    workWithUI()
                }
        }

        startButton.setOnClickListener {
            if (progressBar.progress != 100) {
                task1() //or task2()
                startButton.visibility = View.INVISIBLE
            } else {
                startButton.text = getString(R.string.startBarBtn)
                progressBar.progress = 0
            }

        }
    }
}
