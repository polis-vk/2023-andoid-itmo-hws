package ru.ok.itmo.example

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var delay_time: Long = 100

    private fun task1() {
        findViewById<TextView>(R.id.button).setOnClickListener {
            val t = Thread(Runnable{
                while (findViewById<ProgressBar>(R.id.progress_bar).progress < 100) {
                    runOnUiThread {
                        findViewById<ProgressBar>(R.id.progress_bar).progress++
                    }
                    Thread.sleep(delay_time)
                }
            })
            t.start()
        }
    }

    private fun task2() {
        findViewById<TextView>(R.id.button).setOnClickListener {
            Completable.create {
                while (findViewById<ProgressBar>(R.id.progress_bar).progress < 100){
                    findViewById<ProgressBar>(R.id.progress_bar).progress++
                    Thread.sleep(delay_time)
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        task1()

        findViewById<TextView>(R.id.button_restart).setOnClickListener {
            findViewById<ProgressBar>(R.id.progress_bar).progress = 0
        }
        findViewById<TextView>(R.id.r50).setOnClickListener {
            delay_time = 50
        }
        findViewById<TextView>(R.id.r100).setOnClickListener {
            delay_time = 100
        }
        findViewById<TextView>(R.id.r300).setOnClickListener {
            delay_time = 300
        }
        findViewById<TextView>(R.id.r500).setOnClickListener {
            delay_time = 500
        }
    }
}
