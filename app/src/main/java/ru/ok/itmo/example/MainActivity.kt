package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewModel: MainViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var start: Button
    private lateinit var text: TextView
    private lateinit var radioTypes: RadioGroup
    enum class AsyncType {
        Coroutines, Flow, WithState
    }
    var asyncType: AsyncType = AsyncType.Coroutines
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        progressBar = findViewById(R.id.progressBar)
        text = findViewById(R.id.text_count)

        radioTypes = findViewById(R.id.radio_types)
        radioTypes.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio_coroutines) {
               asyncType = AsyncType.Coroutines
            } else if (checkedId == R.id.radio_flow) {
                asyncType = AsyncType.Flow
            } else if (checkedId == R.id.radio_with_state) {
                asyncType = AsyncType.WithState
            }
        }

        start = findViewById(R.id.button_start)
        start.setOnClickListener {
            runBar()
        }
    }

    private fun runBar() {
        when (asyncType) {
            AsyncType.Coroutines -> runCoroutinesVersion()
            AsyncType.Flow -> runFlowVersion()
            AsyncType.WithState -> runWithState()
        }
    }

    private fun runCoroutinesVersion() {
        MainScope().launch(Dispatchers.IO) {
            repeat(101) {
                delay(100)
                withContext(Dispatchers.Main) {
                    setBar(it)
                }
            }
        }
    }

    private fun runFlowVersion() {
        MainScope().launch(Dispatchers.Main) {
            flow {
                repeat(101) {
                    delay(100)
                    emit(it)
                }
            }.flowOn(Dispatchers.IO).collect {
                setBar(it)
            }
        }
    }
    fun runWithState() {
        viewModel.run().also {
            lifecycleScope.launch {
                viewModel.count.collect {
                    setBar(it)
                }
            }
        }
    }

    private fun setBar(num: Int) {
        progressBar.progress = num
        text.text = num.toString()
    }
}