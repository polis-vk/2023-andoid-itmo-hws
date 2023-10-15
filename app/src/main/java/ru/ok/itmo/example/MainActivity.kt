package ru.ok.itmo.example

import io.reactivex.rxjava3.core.Observable
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var startButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var radioGroup: RadioGroup
    private var maxValue: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startButton = findViewById(R.id.start_button)
        progressBar = findViewById(R.id.progress_bar)
        textView = findViewById(R.id.textView)
        radioGroup = findViewById(R.id.radio)

        startButton.setOnClickListener {

            maxValue = (findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text as String).toInt()

            startButton.isEnabled = false
            rxWork()
        }
    }

    private fun threadWork() {
        val thread = Thread {
            for (i in 1..maxValue) {
                runOnUiThread {
                    changeUI(i)
                }
                Thread.sleep(100)
            }
            runOnUiThread {
                startButton.isEnabled = true
            }
        }
        thread.start()
    }

    private fun rxWork() {
        Observable.range(1, maxValue)
            .concatMap { number: Int -> Observable.just(number).delay(100, TimeUnit.MILLISECONDS) }
            .subscribe(
                { value -> runOnUiThread {changeUI(value) } },
                { error -> System.err.println(error.stackTrace) }
            ) { runOnUiThread {startButton.isEnabled = true} }
    }

    private fun changeUI(n: Int) {
        progressBar.progress = n * 100 / maxValue
        textView.text = n.toString()
    }
}