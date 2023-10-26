package ru.ok.itmo.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var button: Button
    private lateinit var progress: ProgressBar
    private lateinit var view: TextView
    private lateinit var radio50: RadioButton
    private lateinit var radio100: RadioButton
    private lateinit var radio300: RadioButton
    private lateinit var radio500: RadioButton
    private lateinit var button_reset: Button

    private lateinit var button2: Button
    private lateinit var progress2: ProgressBar
    private lateinit var view2: TextView

    @Volatile
    private var delay: Long = 100
    @Volatile
    private var runFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button = findViewById(R.id.button)
        progress = findViewById(R.id.progress)
        view = findViewById(R.id.view)
        radio50 = findViewById(R.id.radio50)
        radio50.setOnClickListener {
            delay = 50
        }
        radio100 = findViewById(R.id.radio100)
        radio100.setOnClickListener {
            delay = 100
        }
        radio300 = findViewById(R.id.radio300)
        radio300.setOnClickListener {
            delay = 300
        }
        radio500 = findViewById(R.id.radio500)
        radio500.setOnClickListener {
            delay = 500
        }
        button_reset = findViewById(R.id.button_reset)
        button.setOnClickListener {
            startProgressBar()
        }
        button_reset.setOnClickListener {
            runFlag = false
            setProgressBar(0)
        }

        button2 = findViewById(R.id.button2)
        progress2 = findViewById(R.id.progress2)
        view2 = findViewById(R.id.view2)
        button2.setOnClickListener {
            startRxProgressBar()
        }
    }

    private fun startProgressBar() {
        val th = Thread {
            runFlag = true
            runOnUiThread {
                setProgressBar(0)
                button.isEnabled = false
            }
            for (i in 0..100) {
                if (!runFlag) {
                    break
                }
                runOnUiThread {
                    setProgressBar(i)
                }
                Thread.sleep(delay)
            }
            runOnUiThread {
                button.isEnabled = true
            }
        }
        th.start()
    }

    private fun startRxProgressBar() {
        button2.isEnabled = false
        Observable.intervalRange(0, 101, 0, 100, TimeUnit.MILLISECONDS, Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                setProgressBar2(it.toInt())
                if (it >= 100L) {
                    button2.isEnabled = true
                }
            }
    }

    private fun setProgressBar(n: Int) {
        progress.progress = n
        view.text = n.toString()
    }

    private fun setProgressBar2(n: Int) {
        progress2.progress = n
        view2.text = n.toString()
    }
}