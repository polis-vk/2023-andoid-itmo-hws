package ru.ok.itmo.example

import MyViewModel
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var progress: ProgressBar
    private lateinit var viewModel : MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = findViewById(R.id.progress_bar)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        val startButton = findViewById<Button>(R.id.start_button)

        startButton.setOnClickListener {
            coroutinesView()
        }

        viewModel.progressLiveData.observe(this)
        { value ->
            progress.progress = value
        }

    }
    private fun coroutines() {
        var i = 0
        MainScope().launch(Dispatchers.IO) {
            repeat(progress.max) {
                delay(100)
                withContext(Dispatchers.Main) {
                    progress.progress = i
                    i++
                }
            }
        }
    }

    private fun flow() {
        var i = 0
        MainScope().launch(Dispatchers.IO) {
            flow {
                repeat(progress.max) {
                    delay(100)
                    emit(i)
                    i++
                }
            }.flowOn(Dispatchers.Main).collect { value ->
                progress.progress = value
            }
        }
    }

    private fun coroutinesView(){
        viewModel.start()
    }
}
