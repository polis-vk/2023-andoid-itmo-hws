package ru.ok.itmo.example

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LiveDataProgressActivity : AppCompatActivity(R.layout.activity_coroutines_progress) {
    private var progressBar: ProgressBar? = null
    private var startButton: Button? = null
    private lateinit var viewModel: ProcessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = findViewById(R.id.progressBar)
        startButton = findViewById(R.id.startProcess)

        startButton?.setOnClickListener { launchProcess() }

        viewModel = ViewModelProvider(this)[ProcessViewModel::class.java]
        viewModel.progress.observe(this) {
            progressBar?.progress = it
        }
    }

    private fun launchProcess() {
        startButton?.isEnabled = false
        MainScope().launch(Dispatchers.Main) { viewModel.startProgress() }
    }
}
