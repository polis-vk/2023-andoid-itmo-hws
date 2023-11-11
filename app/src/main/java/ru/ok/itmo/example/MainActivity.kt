package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var oneToHun:Button
    private lateinit var progress:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        oneToHun = findViewById(R.id.button_bar)
        progress = findViewById(R.id.progressbar_button);
        oneToHun.setOnClickListener {
            cor()
        }
    }
    fun cor() {
        var up = 0
        val dispathIO = CoroutineScope(Dispatchers.IO)
        dispathIO.launch {
            repeat(progress.max) {
                up++
                withContext(Dispatchers.Main) {
                    progress.progress = up
                }
                delay(100);
            }
        }
    }
    fun flowCor() {
        var up = 0
        val dispathIO = CoroutineScope(Dispatchers.IO)
        dispathIO.launch {
             flow {
                repeat(progress.max) {
                    up++
                    emit(up)
                    delay(100)
                }
            }.flowOn(Dispatchers.Main).collect{item -> progress.progress = item}
        }
    }
}
