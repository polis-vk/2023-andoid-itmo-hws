package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Task1Fragment : Fragment() {
    private lateinit var startButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task1, container, false)
        startButton = view.findViewById(R.id.startButton)
        progressBar = view.findViewById(R.id.progressBar)
        progressText = view.findViewById(R.id.progressText)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startButton.setOnClickListener {
            startWork()
            startButton.isEnabled = false
        }
    }

    private fun startWork() {
        MainScope().launch(Dispatchers.IO) {
            for (i in 0..100) {
                launch(Dispatchers.Main) {
                    updateUi(i)
                }
                delay(100)
            }
        }
    }

    private fun updateUi(n: Int) {
        progressBar.progress = n
        progressText.text = "$n"
        if (n == 100) {
            startButton.isEnabled = true
            startButton.text = getString(R.string.restart)
        }
    }
}
