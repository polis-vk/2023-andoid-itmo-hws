package ru.ok.itmo.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

abstract class BaseTaskFragment : Fragment() {
    private lateinit var startButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    protected lateinit var task: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks_process, container, false)
        startButton = view.findViewById(R.id.startButton)
        progressBar = view.findViewById(R.id.progressBar)
        progressText = view.findViewById(R.id.progressText)
        task = view.findViewById(R.id.task)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI()
        startButton.setOnClickListener {
            startWork()
            startButton.isEnabled = false
        }
    }

    abstract fun configureUI()

    abstract fun startWork()

    protected fun updateUi(n: Int) {
        progressBar.progress = n
        progressText.text = "$n"
        if (n == 100) {
            startButton.isEnabled = true
            startButton.text = getString(R.string.restart)
        }
    }
}
