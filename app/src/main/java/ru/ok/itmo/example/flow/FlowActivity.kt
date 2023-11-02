package ru.ok.itmo.example.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.example.R

class FlowActivity : AppCompatActivity(R.layout.progress) {

    private var curFlow: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bar = findViewById<ProgressBar>(R.id.progressBar)
        findViewById<Button>(R.id.startButton).setOnClickListener {
            curFlow?.cancel()
            curFlow = lifecycleScope.launch {
                progressFlow(bar.max).onEach(bar::setProgress).flowOn(Dispatchers.Main).collect()
            }
        }
    }

    private fun progressFlow(max: Int) = flow {
        var cur = 0
        emit(cur)
        while (cur < max) {
            withContext(Dispatchers.IO) {
                delay(100)
            }
            emit(++cur)
        }
        withContext(Dispatchers.Main) {
            Toast.makeText(this@FlowActivity, "Flow Completed!", Toast.LENGTH_LONG).show()
        }
    }
}