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

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val buttonStart = findViewById<Button>(R.id.button_start)
        val textCount = findViewById<TextView>(R.id.text_count)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val radioGroup = findViewById<RadioGroup>(R.id.radio_button_count)

        buttonStart.setOnClickListener {
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedOption)
            val countLoad = (radioButton.text as String).toInt()

            buttonStart.isEnabled = false
            // task_1 Thread and Runnable
//            Thread {
//                for (n in 1..countLoad) {
//                    runOnUiThread {
//                        progressBar.progress = n * 100 / countLoad
//                        textCount.text = "$n"
//                    }
//                    Thread.sleep(100)
//                }
//                runOnUiThread {
//                    buttonStart.isEnabled = true
//                }
//            }.start()

            // task_2 Observable ans Observer
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
}