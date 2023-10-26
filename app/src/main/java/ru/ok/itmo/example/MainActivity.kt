package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    private lateinit var threadWorker: ThreadWorker
    private lateinit var rxWorker: RxWorker

    private val counter: Counter = Counter()
    private var time by Delegates.notNull<Long>()

    private lateinit var buttonThreadWorker: Button
    private lateinit var buttonRxWorker: Button
    private lateinit var radioGroup: RadioGroup

    private fun setValueToBar(value: Int) {
        textView.text = value.toString()
        progressBar.progress = value
    }

    private val updateUi: (value: Int) -> Unit = { value ->
        setValueToBar(value)
    }

    private val endUpdateUi: () -> Unit = {
        setStateWorkBtn(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = findViewById(R.id.progress_bar)
        textView = findViewById(R.id.text_view)

        threadWorker = ThreadWorker(counter, updateUi, endUpdateUi, this)
        rxWorker = RxWorker(counter, updateUi, endUpdateUi, this)

        progressBar.max = Counter.MAX

        buttonThreadWorker = findViewById(R.id.button_thread_worker)
        buttonRxWorker = findViewById(R.id.button_rx_worker)
        radioGroup = findViewById(R.id.radioGroup)

        radioGroupLogic()
        btnResetLogic()
        btnLogic(buttonThreadWorker, threadWorker)
        btnLogic(buttonRxWorker, rxWorker)
    }

    private fun btnLogic(button: Button, worker: Worker) {
        button.setOnClickListener {
            worker.run(time)
            setStateWorkBtn(false)
        }
    }

    private fun btnResetLogic() {
        val btnReset = findViewById<Button>(R.id.button_reset)

        btnReset.setOnClickListener {
            setStateWorkBtn(true)
            counter.initCounter().also { setValueToBar(counter.value) }
            threadWorker.stop()
            rxWorker.stop()
        }
    }

    private fun radioGroupLogic() {
        time = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString().toLong()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            val selectedOption = selectedRadioButton.text.toString()
            time = selectedOption.toLong()
        }
    }

    private fun setStateWorkBtn(state: Boolean) {
        buttonThreadWorker.isEnabled = state
        buttonRxWorker.isEnabled = state
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.isEnabled = state
        }
    }
}