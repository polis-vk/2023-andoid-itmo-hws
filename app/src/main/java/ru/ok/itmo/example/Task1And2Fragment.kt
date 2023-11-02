package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class Task1And2Fragment(private val taskName: String) : Fragment() {

    private lateinit var textView: TextView
    lateinit var progressBar: ProgressBar
    lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_progressbar, container, false)
        textView = view.findViewById(R.id.textView)
        progressBar = view.findViewById(R.id.progressBar)
        button = view.findViewById(R.id.button)
        textView.text = taskName

        button.setOnClickListener { buttonPressed() }

        return view
    }
    abstract fun buttonPressed()
}

class Task1Fragment : Task1And2Fragment("Kotlin Coroutines") {
    override fun buttonPressed() {
        button.isEnabled = false
        lifecycleScope.launch(Dispatchers.IO) {
            for (i in 1..100) {
                delay(100)
                withContext(Dispatchers.Main) {
                    progressBar.progress = i
                }
            }
            withContext(Dispatchers.Main) {
                button.isEnabled = true
            }
        }
    }
}

class Task2Fragment : Task1And2Fragment("Kotlin Flow") {
    override fun buttonPressed() {
        button.isEnabled = false
        lifecycleScope.launch(Dispatchers.IO) {
            flow {
                for (i in 1..100) {
                    delay(100)
                    emit(i)
                }
            }.flowOn(Dispatchers.Main)
                .collect {
                    progressBar.progress = it
                }
            withContext(Dispatchers.Main) {
                button.isEnabled = true
            }
        }
    }
}