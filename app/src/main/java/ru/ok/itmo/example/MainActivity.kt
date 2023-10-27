package ru.ok.itmo.example

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var startButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startButton = findViewById(R.id.startButton)
        progressBar = findViewById(R.id.progressBar)

        fun workWithUI(){
            progressBar.progress++
            if(progressBar.progress == 100){
                startButton.visibility = View.VISIBLE
                startButton.text = getString(R.string.restartBarBtn)
            }
        }


        fun task1(){
            Thread{
                while (progressBar.progress != 100){
                    runOnUiThread { workWithUI() }
                    try {
                        Thread.sleep(100)
                    }catch (e: InterruptedException){
                        e.printStackTrace()
                    }
                }
            }.start()
        }

        fun task2(){
            Observable.interval(100, TimeUnit.MILLISECONDS)
                .takeWhile { progressBar.progress != 100 }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    workWithUI()
                }
        }

        startButton.setOnClickListener {
            if(progressBar.progress != 100){
                task1() //or task2()
                startButton.visibility = View.INVISIBLE
            }else{
                startButton.text = getString(R.string.startBarBtn)
                progressBar.progress = 0
            }

        }
    }

}