package ru.ok.itmo.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.RadioGroup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar);
        val fillButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.fill_button);
        val restartButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.restart_button);
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group);
        var separateThread: Thread;

        fun threadSolution(){
            when (radioGroup.checkedRadioButtonId){
                R.id.button_50 -> separateThread = Thread(Progress(50, progressBar))
                R.id.button_100 -> separateThread = Thread(Progress(100, progressBar))
                R.id.button_300 -> separateThread = Thread(Progress(300, progressBar))
                R.id.button_500 -> separateThread = Thread(Progress(500, progressBar))
                else -> separateThread = Thread(Progress(100, progressBar))
            }
            separateThread.start();
        }

        fun rxJavaSolution(){
            val interval: Long;
            when (radioGroup.checkedRadioButtonId){
                R.id.button_50 -> interval = 50
                R.id.button_100 -> interval = 100
                R.id.button_300 -> interval = 300
                R.id.button_500 -> interval = 500
                else -> interval = 100
            }
            io.reactivex.rxjava3.core.Observable.interval(interval, TimeUnit.MILLISECONDS)
                .takeWhile { progressBar.progress != 100 }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    progressBar.incrementProgressBy(1);
                }

        }

        fillButton.setOnClickListener {
            //threadSolution();
            rxJavaSolution();
        }
        restartButton.setOnClickListener {
            progressBar.setProgress(0);
        }
    }

    class Progress(val timeStamp: Long, val progressBar: ProgressBar): Runnable{
        override fun run() {
            while(progressBar.progress != 100) {
                Thread.sleep(timeStamp);
                progressBar.incrementProgressBy(1);
            }
        }

    }

}