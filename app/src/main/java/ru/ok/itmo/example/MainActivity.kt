package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var buttonStart: Button
    private lateinit var textCount: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var radioGroup: RadioGroup
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonStart = findViewById(R.id.button_start)
        textCount = findViewById(R.id.text_count)
        progressBar = findViewById(R.id.progressBar)
        radioGroup = findViewById(R.id.radio_button_count)

        buttonStart.setOnClickListener {
            buttonStart.isEnabled = false
            withObserver()
        }
    }

    fun getCount(): Int {
        val selectedOption: Int = radioGroup.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedOption)
        return (radioButton.text as String).toInt()
    }

    fun withThread() {
        val countLoad = getCount()
        Thread {
                for (n in 1..countLoad) {
                    runOnUiThread {
                        progressBar.progress = n * 100 / countLoad
                        textCount.text = "$n"
                    }
                    Thread.sleep(100)
                }
                runOnUiThread {
                    buttonStart.isEnabled = true
                }
            }.start()
    }

    @SuppressLint("CheckResult")
    fun withObserver() {
        val countLoad = getCount()
        Observable.range(1, countLoad)
            .concatMap { number: Int -> Observable.just(number).delay(100, TimeUnit.MILLISECONDS) }
            .subscribe(
                { value ->
                    runOnUiThread {
                        progressBar.progress = value * 100 / countLoad
                        textCount.text = "$value"
                    }
                },
                { error -> System.err.println("Error: " + error.stackTrace) }
            ) { runOnUiThread { buttonStart.isEnabled = true } }
    }
}