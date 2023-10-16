package ru.ok.itmo.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    companion object {
        const val TAG = "MainActivity"
    }

    private val viewModel by viewModels<ActivityViewModel>()

    private lateinit var progressBar: ProgressBar
    private lateinit var radioGroup: RadioGroup
    private lateinit var button: Button
    private lateinit var restartButton: Button
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = findViewById(R.id.progressBar)
        radioGroup = findViewById(R.id.radioGroup)
        button = findViewById(R.id.button)
        restartButton = findViewById(R.id.restart_button)
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val rb = radioGroup.findViewById<RadioButton>(i)
            viewModel.changeByText(rb.text.toString())
        }
        createRadioButtons()
        button.setOnClickListener {
            if (viewModel.started) {
                return@setOnClickListener
            }
            progressBar.progress = 0
            startProgressBarRx()
        }
        restartButton.setOnClickListener {
            synchronized(viewModel) {
                if (viewModel.started) {
                    if (disposable != null && !disposable!!.isDisposed) {
                        disposable!!.dispose()
                        viewModel.started = false
                    }
                }
                progressBar.progress = 0
                startProgressBarRx()
            }
        }
    }

    private fun createRadioButtons() {
        radioGroup.removeAllViews()
        var checkedId = 0
        for (i in viewModel.options.indices) {
            val radioButton = layoutInflater.inflate(R.layout.radio_button, null) as RadioButton
            radioButton.layoutParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
            radioButton.text = viewModel.options[i].toString()
            radioButton.id = View.generateViewId()
            if (i == viewModel.currentOption) {
                checkedId = radioButton.id
            }
            radioGroup.addView(radioButton)
        }
        radioGroup.check(checkedId)
    }


    // task 1
    private fun startProgressBar() {
        Thread {
            for (i in 0 until 100) {
                Thread.sleep(100L)
                runOnUiThread {
                    progressBar.progress++
                }
            }
        }.start()
    }

    // task 2, 3
    private fun startProgressBarRx() {
        synchronized(viewModel) {
            if (viewModel.started) {
                return
            }
            viewModel.started = true
        }
        val time = viewModel.getCurrent()
        disposable = Observable.create { emitter ->
            try {
                for (i in 1..100) {
                    if (emitter.isDisposed) {
                        return@create
                    }
                    emitter.onNext(i)
                    Thread.sleep(time)
                }
                emitter.onComplete()
            } catch (e: Throwable) {
                Log.d(TAG, e.stackTraceToString())
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressBar.progress = it
            }, {
                Log.d(TAG, "error: ${it.message}")
            }) {
                viewModel.started = false
            }
    }
}