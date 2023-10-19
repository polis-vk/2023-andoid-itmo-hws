package ru.ok.itmo.example

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class AdditionalActivity : AppCompatActivity(R.layout.activity_additional) {

    private lateinit var buttonStart: Button
    private lateinit var buttonRestart: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var testTextView: TextView

    private lateinit var radioButton50: RadioButton
    private lateinit var radioButton100: RadioButton
    private lateinit var radioButton300: RadioButton
    private lateinit var radioButton500: RadioButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonStart = findViewById(R.id.startButton)
        buttonRestart = findViewById(R.id.restartButton)
        progressBar = findViewById(R.id.progress_bar)
        testTextView = findViewById(R.id.test_tv)

        radioButton50 = findViewById(R.id.radio_50)
        radioButton100 = findViewById(R.id.radio_100)
        radioButton300 = findViewById(R.id.radio_300)
        radioButton500 = findViewById(R.id.radio_500)


        buttonStart.setOnClickListener {
            if (isProgressEmpty()) {
                if (isButtonVisible(buttonStart)) {
                    changeButtonVisibility(buttonStart)
                }

                if (isButtonVisible(buttonRestart)) {
                    changeButtonVisibility(buttonRestart)
                }


                val result = Observable.intervalRange(
                    1, 100, 0, getProgressBarPeriod(), TimeUnit.MILLISECONDS
                )
                    .doOnError {
                    it.message?.let { it1 -> Log.d("HANDLE_IT!!!!!", it1) }
                }.doOnComplete { changeButtonVisibility(buttonRestart) }
                    .observeOn(Schedulers.io())
                    .subscribe {
                        runOnUiThread {
                            progressBar.progress = it.toInt()
                            testTextView.text = it.toString()
                        }

                    }
            }
        }

        buttonRestart.setOnClickListener {
            progressBar.progress = 0
            if (!isButtonVisible(buttonStart)) {
                changeButtonVisibility(buttonStart)
            }
        }
    }

    private fun changeButtonVisibility(button: Button) {
        runOnUiThread {
            if (isButtonVisible(button)) {
                button.visibility = Button.INVISIBLE
            } else {
                button.visibility = Button.VISIBLE
            }
        }
    }

    private fun isButtonVisible(button: Button): Boolean {
        return button.visibility == Button.VISIBLE
    }


    private fun isProgressEmpty(): Boolean {
        return progressBar.progress == 0
    }

    private fun getProgressBarPeriod(): Long {
        return when {
            radioButton50.isChecked -> 50
            radioButton100.isChecked -> 100
            radioButton300.isChecked -> 300
            radioButton500.isChecked -> 500
            else -> 100
        }
    }

}

