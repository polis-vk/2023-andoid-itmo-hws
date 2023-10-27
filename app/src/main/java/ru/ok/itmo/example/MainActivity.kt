package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    private lateinit var buttonCoroutinesWorker: Button
    private lateinit var buttonCoroutinesViewModelWorker: Button
    private lateinit var buttonFlowWorker: Button

    private lateinit var coroutinesWorker: CoroutinesUsualWorker
    private lateinit var coroutinesViewModelWorker: CoroutinesViewModelWorker
    private lateinit var flowWorker: FlowWorker

    private val counter: Counter = Counter()

    companion object
    {
        private const val SLEEP_TIME_MS: Long = 10
    }

    private fun setValueToBar(value: Int) {
        textView.text = value.toString()
        progressBar.progress = value
    }

    private val startUpdateUi: () -> Unit = {
        setStateWorkBtn(false)
    }

    private val updateUi: (value: Int) -> Unit = { value ->
        setValueToBar(value)
    }

    private val endUpdateUi: () -> Unit = {
        setStateWorkBtn(true)
    }

    private fun setStateWorkBtn(state: Boolean) {
        buttonCoroutinesWorker.isEnabled = state
        buttonCoroutinesViewModelWorker.isEnabled = state
        buttonFlowWorker.isEnabled = state
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = findViewById(R.id.progress_bar)
        textView = findViewById(R.id.text_view)

        coroutinesWorker = CoroutinesUsualWorker(counter, startUpdateUi, updateUi, endUpdateUi)
        coroutinesViewModelWorker = ViewModelProvider(this, ViewModelWorkerFactory(counter))[CoroutinesViewModelWorker::class.java]
        flowWorker = FlowWorker(counter)

        progressBar.max = Counter.MAX

        buttonCoroutinesWorker = findViewById(R.id.button_coroutines_worker)
        buttonCoroutinesViewModelWorker = findViewById(R.id.button_coroutines_view_model_worker)
        buttonFlowWorker = findViewById(R.id.button_flow_worker)

        btnUsualLogic(buttonCoroutinesWorker, coroutinesWorker)
        btnViewModelLogic(buttonCoroutinesViewModelWorker, coroutinesViewModelWorker)
        btnFlowLogic(buttonFlowWorker, flowWorker)
    }

    private fun btnUsualLogic(button: Button, usualWorker: Worker<Unit>) {
        button.setOnClickListener {
            usualWorker.run(SLEEP_TIME_MS)
        }
    }

    private fun btnViewModelLogic(button: Button, viewModelWorker: ViewModelWorker) {
        viewModelWorker.liveData.observe(this) { counter ->
            updateUi(counter.value)
        }

        viewModelWorker.active.observe(this) { active ->
            if (active) startUpdateUi()
            else endUpdateUi()
        }

        button.setOnClickListener {
            viewModelWorker.run(SLEEP_TIME_MS)
        }
    }

    private fun btnFlowLogic(button: Button, worker: Worker<Flow<Counter>>) {
        button.setOnClickListener {
            this.lifecycleScope.launch {
                startUpdateUi()
                worker.run(SLEEP_TIME_MS).collect { counter ->
                    updateUi(counter.value)
                }
                endUpdateUi()
            }
        }
    }
}