package ru.ok.itmo.example

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var button: Button
    private lateinit var progress: ProgressBar
    private lateinit var view: TextView

    private lateinit var button2: Button
    private lateinit var progress2: ProgressBar
    private lateinit var view2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button = findViewById(R.id.button)
        progress = findViewById(R.id.progress)
        view = findViewById(R.id.view)
        button.setOnClickListener {
            startCoroutinesBar()
        }
        button2 = findViewById(R.id.button2)
        progress2 = findViewById(R.id.progress2)
        view2 = findViewById(R.id.view2)
        button2.setOnClickListener {
            startFlowBar()
        }
    }

    private fun startCoroutinesBar() {
        button.isEnabled = false
        lifecycleScope.launch(Dispatchers.IO) {
            for (i in 0..100) {
                withContext(Dispatchers.Main) {
                    renderView(i)
                }
                Thread.sleep(100)
            }
            withContext(Dispatchers.Main) {
                button.isEnabled = true
            }
        }
    }

    private fun startFlowBar() {
        button2.isEnabled = false
        lifecycleScope.launch {
            flow {
                for (i in 0..100) {
                    emit(i)
                    delay(100)
                }
            }.flowOn(Dispatchers.IO).onEach {
                renderView2(it)
                if (it >= 100) {
                    button2.isEnabled = true
                }
            }.flowOn(Dispatchers.Main).collect {}
        }
    }

    fun renderView(n: Int) {
        progress.progress = n
        view.text = n.toString()
    }

    fun renderView2(n: Int) {
        progress2.progress = n
        view2.text = n.toString()
    }
}